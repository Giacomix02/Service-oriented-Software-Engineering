import './style.css';
import axios from 'axios';

const DAAS = 'http://127.0.0.1:5000';
const EAAS = 'http://127.0.0.1:5001';

// DOM references (will be populated on DOMContentLoaded)
let errorBanner;
let errorModal;
let errorModalMessage;
let errorModalClose;
let loading;
let placeholder;
let resultsHeader;
let resultsTitle;
let resultsCount;
let resultsGrid;
let poiDetail;
let btnBackList;
let selectMunicipality;
let selectSubject;
let btnEvaluate;
let btnFind;


// Helpers
function showError(msg) {
    if (errorModalMessage) errorModalMessage.textContent = msg;
    if (errorModal) errorModal.style.display = 'flex';
}

function hideError() {
    if (errorModal) errorModal.style.display = 'none';
}

function setLoading(on) {
    loading.style.display = on ? 'flex' : 'none';
    placeholder.style.display = on ? 'none' : placeholder.style.display;
}

function showView(view) {
    placeholder.style.display = view === 'placeholder' ? 'flex' : 'none';
    resultsHeader.style.display = view === 'results' ? 'flex' : 'none';
    // Use grid to layout result cards; set template and gap so cards stack correctly
    if (view === 'results') {
        resultsGrid.style.display = 'grid';
        resultsGrid.style.gridTemplateColumns = '1fr'; // stack vertically; change if multi-column desired
        resultsGrid.style.gap = '12px';
    } else {
        resultsGrid.style.display = 'none';
        resultsGrid.style.gridTemplateColumns = '';
        resultsGrid.style.gap = '';
    }
    poiDetail.style.display = view === 'detail' ? 'flex' : 'none';
}

// Robust api GET with timeout + retries
async function apiGet(path, retries = 2, timeout = 5000) {
    const url = DAAS + path;
    // Keyword search needs more time and retries
    if (path.includes('/keyword/')) {
        timeout = 20000;
        retries = 3;
    }
    for (let attempt = 0; attempt <= retries; attempt++) {
        try {
            const res = await axios.get(url, { timeout });
            return res.data.data ?? res.data;
        } catch (err) {
            const last = attempt === retries;
            console.error(`apiGet error (${attempt})`, url, err.message);
            if (last) {
                throw new Error(`Unable to fetch ${path}: ${err.message}`);
            }
            // exponential backoff
            const wait = 300 * Math.pow(2, attempt);
            await new Promise(r => setTimeout(r, wait));
        }
    }
}

async function apiPost(base, path, body) {
    const res = await axios.post(base + path, body);
    console.log(res);
    return res.data.data ?? res.data;
}

function getPreferences() {
    const visitDateEl = document.getElementById('form-visit-date');
    const contextEl = document.getElementById('form-context');

    return {
        accessibility: Boolean(document.getElementById('form-accessibility')?.checked),
        language: Boolean(document.getElementById('form-language')?.checked),
        allergies: Boolean(document.getElementById('form-allergy')?.checked),
        pollution: Boolean(document.getElementById('form-pollution')?.checked),
        context: contextEl?.value?.trim() ?? '',
        visitDate: visitDateEl?.value?.trim() ?? '',
    };
}

function requireVisitDate() {
    const visitDate = getPreferences().visitDate;
    if (!visitDate) throw new Error('Please insert a visit date before evaluating.');
    return visitDate;
}

// Init: populate dropdowns
async function init() {
    try {
        const results = await Promise.allSettled([loadMunicipalities(), loadSubjects()]);
        results.forEach((r, idx) => {
            if (r.status === 'rejected') {
                const which = idx === 0 ? 'municipalities' : 'subjects';
                showError(`Failed to load ${which}: ${r.reason?.message ?? r.reason}`);
            }
        });
    } catch (e) {
        console.error('Unexpected init error', e);
        showError('Unexpected error during initialization.');
    }
}

async function loadMunicipalities() {
    const sel = document.getElementById('select-municipality');
    if (!sel) throw new Error('select-municipality element not found');
    const data = await apiGet('/municipalities');
    if (!Array.isArray(data)) throw new Error('Invalid municipalities response');
    // keep the first default option, remove others to avoid duplicates
    while (sel.options.length > 1) sel.remove(1);
    if (data.length === 0) {
        console.warn('No municipalities returned');
        showError('No municipalities available.');
        return;
    }
    data.forEach(item => {
        const municipality = item?.municipality ?? item?.name ?? item;
        if (!municipality) return;
        // avoid duplicates
        if ([...sel.options].some(o => o.value === municipality)) return;
        const opt = document.createElement('option');
        opt.value = opt.textContent = municipality;
        sel.appendChild(opt);
    });
}

async function loadSubjects() {
    const sel = document.getElementById('select-subject');
    if (!sel) throw new Error('select-subject element not found');
    const data = await apiGet('/subjects');
    if (!Array.isArray(data)) throw new Error('Invalid subjects response');
    // keep the first default option, remove others to avoid duplicates
    while (sel.options.length > 1) sel.remove(1);
    if (data.length === 0) {
        console.warn('No subjects returned');
        showError('No subjects available.');
        return;
    }
    data.forEach(item => {
        const subject = item?.subject ?? item?.name ?? item;
        if (!subject) return;
        if ([...sel.options].some(o => o.value === subject)) return;
        const opt = document.createElement('option');
        opt.value = opt.textContent = subject;
        sel.appendChild(opt);
    });
}

// Tabs
// document.querySelectorAll('.tab-btn').forEach(...)  <-- moved into setupDOM()

// Fetch POIs from DaaS based on active tab
async function fetchPois() {
    const activeTab = document.querySelector('.tab-btn.active')?.dataset.tab ?? 'keyword';

    if (activeTab === 'keyword') {
        const keyword = document.getElementById('input-keyword').value.trim();
        if (!keyword) {
            throw new Error('Please enter a keyword to search.');
        }
        return await apiGet(`/pois/keyword/${encodeURIComponent(keyword)}`);
    }

    if (activeTab === 'filters') {
        console.log(selectMunicipality, selectSubject)
        const mun = selectMunicipality.value;
        const sub = selectSubject.value;
        let pois = [];
        if (mun) {
            pois = await apiGet(`/pois/municipality/${encodeURIComponent(mun)}`);
            if (sub) pois = pois.filter(p => p.subject === sub);
        } else if (sub) {
            pois = await apiGet(`/pois/subject/${encodeURIComponent(sub)}`);
        } else {
            showError("Please select a municipality or a subject.")
        }
        return pois;
    }

    if (activeTab === 'geo') {
        const lat = parseFloat(document.getElementById('input-lat').value);
        const lon = parseFloat(document.getElementById('input-long').value);
        const delta = parseFloat(document.getElementById('input-delta').value);
        if (isNaN(lat) || isNaN(lon)) throw new Error('Please provide valid coordinates.');
        return !isNaN(delta)
            ? await apiPost(DAAS, '/pois/nearest', {lat, lon, delta})
            : await apiPost(DAAS, '/pois/position', {lat, lon});
    }

    return [];
}

// Evaluate a single POI against EaaS
async function evaluatePoi(poiId, prefs) {
    const currentPrefs = prefs ?? getPreferences();
    const visitDate = currentPrefs.visitDate || requireVisitDate();

    const payload = {
        poiId,
        accessibility: currentPrefs.accessibility,
        language: currentPrefs.language,
        allergies: currentPrefs.allergies,
        pollution: currentPrefs.pollution,
        context: currentPrefs.context,
        visitDate,
    };

    console.log('evaluate payload', payload);
    return await apiPost(EAAS, '/evaluate', payload);
}

// Boot: wait DOM ready, then wire DOM refs, listeners and init
function setupDOM() {
    // populate DOM references
    errorBanner = document.getElementById('error-banner');
    errorModal = document.getElementById('error-modal');
    errorModalMessage = document.getElementById('error-modal-message');
    errorModalClose = document.getElementById('error-modal-close');
    loading = document.getElementById('loading');
    placeholder = document.getElementById('placeholder');
    resultsHeader = document.getElementById('results-header');
    resultsTitle = document.getElementById('results-title');
    resultsCount = document.getElementById('results-count');
    resultsGrid = document.getElementById('results-grid');
    poiDetail = document.getElementById('poi-detail');
    btnBackList = document.getElementById('btn-back-list');
    selectMunicipality = document.getElementById('select-municipality');
    selectSubject = document.getElementById('select-subject');
    btnEvaluate = document.getElementById('btn-evaluate');
    btnFind = document.getElementById('btn-find');

    if (errorModalClose) errorModalClose.addEventListener('click', hideError);
    if (errorModal) {
        errorModal.addEventListener('click', (e) => {
            if (e.target.classList.contains('modal-backdrop')) hideError();
        });
    }

    // Tabs
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.addEventListener('click', e => {
            document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
            document.querySelectorAll('.search-panel').forEach(p => p.classList.remove('active'));
            e.target.classList.add('active');
            document.getElementById(`panel-${e.target.dataset.tab}`).classList.add('active');
        });
    });

    // Back button
    if (btnBackList) btnBackList.addEventListener('click', () => showView('results'));

    // Evaluate button (evaluate batch of POIs)
    if (btnEvaluate) {
        btnEvaluate.addEventListener('click', async () => {
            setLoading(true);
            showView('placeholder');

            let pois = [];
            try {
                pois = await fetchPois();
            } catch (e) {
                showError(e.message || 'Error fetching attractions.');
                setLoading(false);
                showView('placeholder');
                return;
            }

            if (!pois || pois.length === 0) {
                setLoading(false);
                showError('No attractions found for the selected criteria.');
                showView('placeholder');
                return;
            }

            const prefs = getPreferences();
            const batch = pois.slice(0, 10);

            const evaluations = await Promise.allSettled(
                batch.map(poi => evaluatePoi(poi.id, prefs).then(result => ({ poi, result })))
            );

            const evaluated = evaluations.map(e =>
                e.status === 'fulfilled'
                    ? e.value
                    : { poi: e.reason?.poi ?? null, result: null }
            );

            setLoading(false);
            renderResults(evaluated, pois.length);
        });
    }

    // Find button (show POI cards without evaluating)
    if (btnFind) {
        btnFind.addEventListener('click', async () => {
            setLoading(true);
            showView('placeholder');

            let pois = [];
            try {
                pois = await fetchPois();
            } catch (e) {
                showError(e.message || 'Error fetching attractions.');
                setLoading(false);
                showView('placeholder');
                return;
            }

            setLoading(false);
            if (!pois || pois.length === 0) {
                showError('No attractions found for the selected criteria.');
                showView('placeholder');
                return;
            }

            // Show cards for all found POIs without evaluating them yet.
            const evaluated = pois.map(poi => ({ poi, result: null }));
            renderResults(evaluated, pois.length);
        });
    }

    // Mutual-exclusive select behaviour
    if (selectSubject) {
        selectSubject.addEventListener('change', () => {
            if (selectMunicipality) {
                selectMunicipality.value = '';
                selectMunicipality.classList.remove('light-up-selected-filter');
            }
            selectSubject.classList.add('light-up-selected-filter');
        });
    }
    if (selectMunicipality) {
        selectMunicipality.addEventListener('change', () => {
            if (selectSubject) {
                selectSubject.value = '';
                selectSubject.classList.remove('light-up-selected-filter');
            }
            selectMunicipality.classList.add('light-up-selected-filter');
        });
    }
}

// Wait DOM ready then setup and init
document.addEventListener('DOMContentLoaded', () => {
    try {
        setupDOM();
        init();
    } catch (e) {
        console.error('Error during DOM setup', e);
        if (typeof showError === 'function') showError('Initialization error.');
    }
});

// Render results grid (updated: adds Evaluate button per card)
function renderResults(evaluated, total) {
    resultsGrid.innerHTML = '';

    const found = evaluated.filter(e => e.poi).length;
    resultsTitle.textContent = 'Results';
    resultsCount.textContent = `${found} attraction${found !== 1 ? 's' : ''}`
        + (found < total ? ` (showing first ${found} of ${total})` : '');

    evaluated.forEach(entry => {
        const poi = entry.poi;
        let result = entry.result;
        if (!poi) return;

        const decision = result?.decision?.final_decision ?? null;
        const verdict = decision ? decision.toLowerCase() : 'unknown';

        const card = document.createElement('div');
        card.className = 'result-card';
        card.innerHTML = `
      <div class="result-card-stripe stripe-${verdict}"></div>
      <div class="result-card-body">
        <div class="result-card-top">
          <h3>${poi.title ?? 'Untitled'}</h3>
          <span class="badge ${decision ? `badge-${verdict}` : ''}">${decision ?? 'N/A'}</span>
        </div>
        <p class="result-card-location">${poi.municipality ?? ''}</p>
        ${poi.comment ? `<p class="result-card-snippet">${poi.comment}</p>` : ''}
        <div class="result-card-actions"></div>
      </div>
    `;

        card.addEventListener('click', () => showDetail(poi, result));

        const actionsContainer = card.querySelector('.result-card-actions');
        const evalBtn = document.createElement('button');
        evalBtn.className = 'btn-evaluate';
        evalBtn.textContent = result ? 'Evaluated' : 'Evaluate';
        actionsContainer.appendChild(evalBtn);

        if (result) evalBtn.disabled = true;

        evalBtn.addEventListener('click', async (e) => {
            e.stopPropagation();

            const prefs = getPreferences();
            if (!prefs.visitDate) {
                showError('Please insert a visit date before evaluating.');
                return;
            }

            evalBtn.disabled = true;
            const prevText = evalBtn.textContent;
            evalBtn.textContent = 'Evaluating…';
            setLoading(true);

            try {
                const res = await evaluatePoi(poi.id, prefs);
                result = res;

                const decisionNow = res?.decision?.final_decision ?? null;
                const verdictNow = decisionNow ? decisionNow.toLowerCase() : 'unknown';
                const stripeEl = card.querySelector('.result-card-stripe');
                if (stripeEl) stripeEl.className = `result-card-stripe stripe-${verdictNow}`;
                const badgeEl = card.querySelector('.badge');
                if (badgeEl) {
                    badgeEl.textContent = decisionNow ?? 'N/A';
                    badgeEl.className = `badge badge-${verdictNow}`;
                }

                evalBtn.textContent = 'Re-evaluate';
                evalBtn.disabled = false;
                setLoading(false);
                showDetail(poi, res);
            } catch (err) {
                console.error('Evaluation error', err);
                setLoading(false);
                showError('Evaluation failed: ' + (err.message || err));
                evalBtn.disabled = false;
                evalBtn.textContent = prevText;
            }
        });

        resultsGrid.appendChild(card);
    });

    showView('results');
}

// Show detail view
function showDetail(poi, result) {
    const decisionData = result?.decision ?? {};
    const decision = decisionData.final_decision ?? null;
    const riskLevel = decisionData.risk_level ?? null;
    const verdict = decision ? decision.toLowerCase() : 'unknown';

    const detailBadge = document.getElementById('detail-badge');
    detailBadge.textContent = decision ?? '—';
    detailBadge.className = `badge badge-${verdict}`;

    const riskBadge = document.getElementById('detail-risk-badge');
    if (riskLevel) {
        riskBadge.textContent = riskLevel;
        riskBadge.className = `badge badge-${riskLevel.toLowerCase()}`;
        riskBadge.style.display = '';
    } else {
        riskBadge.style.display = 'none';
    }

    document.getElementById('detail-title').textContent = poi.title ?? '';
    document.getElementById('detail-meta').textContent = poi.municipality ?? '';
    document.getElementById('detail-desc').textContent =
        poi.comment ?? '';

    const img = document.getElementById('detail-img');
    if (poi.image) {
        img.src = poi.image;
        img.style.display = 'block';
    } else {
        img.style.display = 'none';
    }

    document.getElementById('eval-justification').textContent =
        decisionData.justification ?? (result ? 'No justification provided.' : 'Evaluation failed.');

    const actionsList = document.getElementById('eval-actions');
    const actionsCard = document.getElementById('eval-actions-card');
    actionsList.innerHTML = '';
    const actions = decisionData.required_action ?? [];
    if (actions.length > 0) {
        actions.forEach(act => {
            const li = document.createElement('li');
            li.textContent = act;
            actionsList.appendChild(li);
        });
        actionsCard.style.display = '';
    } else {
        actionsCard.style.display = 'none';
    }

    const auditCard = document.getElementById('audit-card');
    const auditLog = document.getElementById('audit-log');
    if (auditLog) {
        auditLog.textContent = result?.audit ?? 'No audit log available.';
    }
    if (auditCard) {
        auditCard.style.display = 'block';
    }

    const detailFields = document.getElementById('detail-fields');
    const detailFieldsCard = document.getElementById('detail-fields-card');
    const fields = [
        ['Final decision', decision ?? '—'],
        ['Risk level', riskLevel ?? '—'],
        ['Justification', decisionData.justification ?? '—'],
        ['Required actions', actions.length ? actions.join(' | ') : '—'],
        ['Audit ID', decisionData.audit_id ?? '—'],
        ['POI ID', result?.poiId ?? '—'],
        ['Timestamp', result?.timestamp ?? '—'],
    ];

    detailFields.innerHTML = '';
    fields.forEach(([label, value]) => {
        const row = document.createElement('li');
        row.innerHTML = `<strong>${label}:</strong> <span>${String(value)}</span>`;
        detailFields.appendChild(row);
    });

    detailFieldsCard.style.display = 'block';

    showView('detail');
}
