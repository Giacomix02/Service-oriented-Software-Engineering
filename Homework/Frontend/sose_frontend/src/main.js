import './style.css';
import axios from 'axios';

const DAAS = 'http://127.0.0.1:5000';
const EAAS = 'http://127.0.0.1:5001';

// DOM references
const errorBanner = document.getElementById('error-banner');
const loading = document.getElementById('loading');
const placeholder = document.getElementById('placeholder');
const resultsHeader = document.getElementById('results-header');
const resultsTitle = document.getElementById('results-title');
const resultsCount = document.getElementById('results-count');
const resultsGrid = document.getElementById('results-grid');
const poiDetail = document.getElementById('poi-detail');
const btnBackList = document.getElementById('btn-back-list');

// Helpers
function showError(msg) {
  errorBanner.textContent = msg;
  errorBanner.style.display = 'block';
  setTimeout(() => (errorBanner.style.display = 'none'), 5000);
}

function setLoading(on) {
  loading.style.display = on ? 'flex' : 'none';
  placeholder.style.display = on ? 'none' : placeholder.style.display;
}

function showView(view) {
  placeholder.style.display = view === 'placeholder' ? 'flex' : 'none';
  resultsHeader.style.display = view === 'results' ? 'flex' : 'none';
  resultsGrid.style.display = view === 'results' ? 'flex' : 'none';
  poiDetail.style.display = view === 'detail' ? 'flex' : 'none';
}

async function apiGet(path) {
  const res = await axios.get(DAAS + path);
  return res.data.data ?? res.data;
}

async function apiPost(base, path, body) {
  const res = await axios.post(base + path, body);
  console.log(res);
  return res.data.data ?? res.data;
}

function getPreferences() {
  return {
    accessibility: document.getElementById('form-accessibility').checked,
    language: document.getElementById('form-language').checked,
    allergies: document.getElementById('form-allergy').checked,
    pollution: document.getElementById('form-pollution').checked,
    context: document.getElementById('form-context').value.trim(),
    visitDate: document.getElementById('form-visit-date').value.trim(),
  };
}

// Init: populate dropdowns
async function init() {
  try {
    await Promise.all([loadMunicipalities(), loadSubjects()]);
  } catch {
    showError('Failed to load initial data, check if DaaS is running.');
  }
}

async function loadMunicipalities() {
  const data = await apiGet('/municipalities');
  const sel = document.getElementById('select-municipality');
  data.forEach(({ municipality }) => {
    const opt = document.createElement('option');
    opt.value = opt.textContent = municipality;
    sel.appendChild(opt);
  });
}

async function loadSubjects() {
  const data = await apiGet('/subjects');
  const sel = document.getElementById('select-subject');
  data.forEach(({ subject }) => {
    const opt = document.createElement('option');
    opt.value = opt.textContent = subject;
    sel.appendChild(opt);
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

// Fetch POIs from DaaS based on active tab
async function fetchPois() {
  const activeTab = document.querySelector('.tab-btn.active')?.dataset.tab ?? 'keyword';

  if (activeTab === 'keyword') {
    const keyword = document.getElementById('input-keyword').value.toLowerCase().trim();
    const allPois = await apiGet('/pois');
    return keyword
      ? allPois.filter(p =>
        p.title?.toLowerCase().includes(keyword) ||
        p.description?.toLowerCase().includes(keyword)
      )
      : allPois;
  }

  if (activeTab === 'filters') {
    const mun = document.getElementById('select-municipality').value;
    const sub = document.getElementById('select-subject').value;
    let pois = [];
    if (mun) {
      pois = await apiGet(`/pois/municipality/${encodeURIComponent(mun)}`);
      if (sub) pois = pois.filter(p => p.subject === sub);
    } else if (sub) {
      pois = await apiGet(`/pois/subject/${encodeURIComponent(sub)}`);
    } else {
      pois = await apiGet('/pois');
    }
    return pois;
  }

  if (activeTab === 'geo') {
    const lat = parseFloat(document.getElementById('input-lat').value);
    const lon = parseFloat(document.getElementById('input-long').value);
    const delta = parseFloat(document.getElementById('input-delta').value);
    if (isNaN(lat) || isNaN(lon)) throw new Error('Please provide valid coordinates.');
    return !isNaN(delta)
      ? await apiPost(DAAS, '/pois/nearest', { lat, lon, delta })
      : await apiPost(DAAS, '/pois/position', { lat, lon });
  }

  return [];
}

// Evaluate a single POI against EaaS
async function evaluatePoi(poiId, prefs) {
  return await apiPost(EAAS, '/evaluate', {
    poiId: poiId,
    accessibility: prefs.accessibility,
    language: prefs.language,
    allergies: prefs.allergies,
    pollution: prefs.pollution,
    context: prefs.context,
    visitDate: prefs.visitDate,
  });
}

// Find & Evaluate button
document.getElementById('btn-find-evaluate').addEventListener('click', async () => {
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

// Render results grid
function renderResults(evaluated, total) {
  resultsGrid.innerHTML = '';

  const found = evaluated.filter(e => e.poi).length;
  const capped = pois => pois < total;
  resultsTitle.textContent = 'Results';
  resultsCount.textContent = `${found} attraction${found !== 1 ? 's' : ''} evaluated`
    + (found < total ? ` (showing first ${found} of ${total})` : '');

  evaluated.forEach(({ poi, result }) => {
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
          ${decision
        ? `<span class="badge badge-${verdict}">${decision}</span>`
        : `<span class="badge" style="opacity:.4">N/A</span>`
      }
        </div>
        <p class="result-card-location">${poi.municipality ?? ''}</p>
        ${poi.short_description || poi.description
        ? `<p class="result-card-snippet">${poi.short_description ?? poi.description}</p>`
        : ''
      }
      </div>
    `;
    card.addEventListener('click', () => showDetail(poi, result));
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
    poi.description ?? poi.short_description ?? '';

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

  const auditEl = document.getElementById('detail-audit-id');
  auditEl.textContent = decisionData.audit_id ? `Audit ID: ${decisionData.audit_id}` : '';

  showView('detail');
}

// Back button
btnBackList.addEventListener('click', () => showView('results'));

// Boot
init();