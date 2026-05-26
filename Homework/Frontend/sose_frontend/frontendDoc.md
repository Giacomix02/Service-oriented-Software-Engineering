# Attraction Recommender

**Frontend Project Documentation**

---

## Overview

Attraction Recommender is a Vite-based single-page application that lets users discover and evaluate points of interest (POIs). It connects to two local backend services — a Data-as-a-Service (DaaS) and an Evaluation-as-a-Service (EaaS) — to retrieve attractions and assess them against personalised user preferences through a policy engine.

---

## Architecture

### Tech Stack

| Layer | Technology |
|---|---|
| Bundler | Vite |
| Language | Vanilla JavaScript (ES modules) |
| HTTP client | Axios |
| Styling | Plain CSS (`style.css`) |
| Entry point | `src/main.js` |

### Backend Services

The app communicates with two services running on localhost:

| Service | Default URL | Purpose |
|---|---|---|
| DaaS | `localhost:5000` | Serves POI data (municipalities, subjects, attraction details) |
| EaaS | `localhost:5001` | Evaluates attractions against user preferences via a policy engine |

---

## UI Layout

The interface is split into two panels:

- **Left panel** — search controls and user preference form
- **Right panel** — results list and POI detail view

### Search Modes

Users can switch between three search tabs:

- **Keyword** — free-text search against the DaaS keyword endpoint (longer timeout: 20s, 3 retries).
- **Filters** — dropdown selectors for municipality and subject. The two filters are mutually exclusive; selecting one clears the other.
- **Geo** — coordinate-based lookup. Providing a delta searches a bounding area; omitting it searches the exact position.

### User Preferences

Before evaluating an attraction the user must fill in the preferences form:

- Visit date *(required — DD/MM/YYYY)*
- Prefer English content
- Accessibility needs
- Pollen allergy
- Avoid overtourism / pollution
- Free-text additional context

---

## Application Flow

### Initialisation

On `DOMContentLoaded` the app calls `setupDOM()` to wire up all event listeners, then `init()` which concurrently loads the municipality and subject dropdowns from the DaaS. Failures are surfaced per-dropdown without blocking the rest of startup.

### Find & Evaluate

1. User selects a search mode, fills in the query, and clicks **Find**.
2. `fetchPois()` calls the appropriate DaaS endpoint and returns a list of POI objects.
3. `renderResults()` renders one card per POI in the right panel, showing the title, municipality, and a short description snippet.
4. Each card has an individual **Evaluate** button. Clicking it calls `evaluatePoi()`, which POSTs the POI ID and user preferences to EaaS.
5. The policy verdict (`final_decision`, `risk_level`, `justification`, `required_action`) updates the card badge and opens the detail view.

### Detail View

Clicking a card or completing an evaluation opens `showDetail()`, which populates:

- Verdict and risk badges
- POI image (hidden when absent)
- Policy verdict card with full justification text
- Audit log card
- Required actions card (hidden when empty)
- Evaluation details table (decision, risk level, audit ID, POI ID, timestamp)

---

## API Helpers

### `apiGet(path, retries, timeout)`

GET wrapper around Axios with automatic retry and exponential backoff (300ms base). Default: 2 retries, 5s timeout. Keyword searches use 3 retries and a 20s timeout. Returns `res.data.data ?? res.data`.

### `apiPost(base, path, body)`

Thin POST wrapper. Used by the geo tab to call `/pois/nearest` and `/pois/position` on DaaS.

---

## DaaS Endpoints (port 5000)

| Path | Method | Description |
|---|---|---|
| `/municipalities` | GET | List of all municipalities for the filter dropdown |
| `/subjects` | GET | List of all subjects for the filter dropdown |
| `/pois/keyword/:kw` | GET | Full-text search for attractions matching keyword |
| `/pois/municipality/:m` | GET | All POIs in a given municipality |
| `/pois/subject/:s` | GET | All POIs with a given subject |
| `/pois/nearest` | POST | POIs within a bounding delta around coordinates |
| `/pois/position` | POST | POIs at an exact coordinate position |

---

## EaaS Endpoint (port 5001)

`POST /evaluate` — accepts a JSON body with the POI ID and user preferences, and returns a policy decision object:

| Field | Description |
|---|---|
| `final_decision` | Overall verdict (e.g. `APPROVED`, `REJECTED`, `CONDITIONAL`) |
| `risk_level` | Severity rating |
| `justification` | Human-readable explanation |
| `required_action` | Array of actions the visitor must take |
| `audit_id` | Identifier for the evaluation record |

---

## Error Handling

Errors are surfaced through a modal dialog (`#error-modal`). The `showError()` helper sets the message and makes the modal visible; the Close button calls `hideError()`. Network errors from `apiGet()` include the HTTP path in the message to aid debugging.

---

## Running the Project

**Prerequisites:** Node.js with npm, and both backend services running locally.

```bash
npm install
npm run dev
```

Vite will start a development server (default: `http://localhost:5173`). Both DaaS (port 5000) and EaaS (port 5001) must be running before using the app.