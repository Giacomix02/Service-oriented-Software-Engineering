# SoSe - Attraction Evaluator & Finder

### Team Members
- Giacomo Paolocci
- Raffaele Lorusso
- Carla Rubio Espineira

---

## Overview

**SoSe** is a two-service system for evaluating tourist Points of Interest (POIs) against a set of configurable policies. It combines a **Data-as-a-Service (DaaS)** layer for querying POI data from an RDF dataset, and an **Ethics-as-a-Service (EaaS)** layer that applies AI-powered policy checks to produce a risk-scored decision.

---

## Architecture

```
User Request
     │
     ▼
[EaaS API :5001]  ──── fetches POI data ────▶  [DaaS API :5000]
     │                                                 │
     │                                        attrattori.rdf (RDF dataset)
     │
     ├── loads policies.json
     ├── filters relevant policies
     ├── calls LLM (Gemma via Google Gemini API)
     └── returns decision + audit log
```

---

## Services

### DaaS — Data as a Service (`apiDaaS.py`)

Serves POI data from an RDF dataset (`attrattori.rdf`) via a Flask REST API on port `5000`.

**Run:**
```bash
python apiDaaS.py
```

### EaaS — Ethics as a Service (`apiEaaS.py`)

Receives requests, fetches POI data from DaaS, applies policies via LLM, and returns a structured decision with audit trail. Runs on port `5001`.

**Run:**
```bash
python apiEaaS.py
```

> ⚠️ A valid `GEMINI_TOKEN` must be set in a `.env` file at the project root.

---

## Evaluate Endpoint

**POST** `http://127.0.0.1:5001/evaluate`

### Request Body

```json
{
  "poiId": 5033571,
  "accessibility": false,
  "language": true,
  "allergies": true,
  "pollution": true,
  "context": "I have osteoarthritis",
  "visitDate": "14/11/2026"
}
```

| Field | Type | Description |
|---|---|---|
| `poiId` | `int` | Unique identifier of the Point of Interest |
| `accessibility` | `bool` | Whether the user has accessibility requirements |
| `language` | `bool` | Whether the user prefers content in English |
| `allergies` | `bool` | Whether the user has pollen allergies |
| `pollution` | `bool` | Whether the user wants to avoid overtourism/pollution |
| `context` | `string` | Free-text additional context (e.g. health conditions) |
| `visitDate` | `string` | Planned visit date in `DD/MM/YYYY` format |

### Example Response

```json
{
  "data": {
    "audit": "2026-05-24 23:24:54.745174: Received request...\n...",
    "decision": {
      "audit_id": "agliano_ancient_village",
      "final_decision": "PASS",
      "justification": "The POI is an ancient village with nature trails. The user has osteoarthritis, which may impact accessibility, but there are no specific policies against it. The content is in Italian, triggering the language policy. Pollen risks are low in November.",
      "required_action": [
        "Translate the description of Agliano from Italian to English."
      ],
      "risk_level": "LOW"
    },
    "poiId": 5033571,
    "timestamp": "2026-05-24 23:25:09.905339"
  },
  "status": "ok"
}
```

---

## Policies

Policies are defined in `policies.json` and are selectively applied based on the user's request flags.

| Policy ID | Trigger | Decision | Risk Weight |
|---|---|---|---|
| `accessibility_policy` | POI has no accessibility information and user has accessibility needs | `REVISE` | 6 |
| `language_policy` | Content is only in Italian but user prefers English | `REVISE` | 3 |
| `overtourism_policy` | High visitor concentration causing pollution/overtourism | `REJECT` / `ESCALATE` | 5 |
| `pollen_allergies_policy` | User has pollen allergy and POI has high pollen risk | `REJECT` | 8 |

### Risk Levels

| Score Range | Risk Level | Decision |
|---|---|---|
| 0 – 3 | LOW | `PASS` |
| 4 – 6 | MEDIUM | `REVISE` |
| 7+ | HIGH | `ESCALATE` |

> A `REJECT` decision from any single policy overrides the score-based outcome.

---

## Pipeline

```
1. Receive request (poiId, flags, context, visitDate)
2. Fetch POI data from DaaS
3. Load and filter policies (based on user flags)
4. Send filtered policies + POI data to LLM
5. LLM evaluates each policy → triggered / passed
6. Calculate risk score from triggered policies
7. Determine risk level and final decision
8. Return decision + justification + audit log
```

---

## LLM Integration

The system uses the `gemma-4-31b-it` model via the Google Gemini API. The LLM receives the POI data, the filtered policies, the visit date, and any extra context. It returns a structured JSON response validated against Pydantic schemas (`EaaSDecision`, `AppliedPolicy`).

The LLM is instructed to infer missing information where possible (e.g. pollen levels from the visit month, or accessibility from the nature of the place).

---

## DaaS API Reference

Full documentation: [`apiDaaSDoc.md`](apiDaaSDoc.md)

| Endpoint | Method | Description |
|---|---|---|
| `/pois/<id>` | GET | Get a single POI by ID |
| `/pois` | GET | Get all POIs |
| `/pois/basic` | GET | Get all POIs (basic info only) |
| `/pois/municipality/<name>` | GET | Get POIs by municipality |
| `/municipalities` | GET | List all municipalities |
| `/pois/position` | POST | Find POI at exact coordinates |
| `/pois/nearest` | POST | Find nearest POI within a delta |
| `/pois/subject/<subject>` | GET | Get POIs by subject |
| `/subjects` | GET | List all subjects |

---

## EaaS API Reference

Full documentation: [`apiEaaSDoc.md`](apiEaaSDoc.md)

| Endpoint | Method | Description |
|---|---|---|
| `/evaluate` | POST | Submit a POI for policy evaluation |

---


## Setup

### Requirements

```bash
pip install flask requests google-generativeai python-dotenv rdflib pydantic
```

### Environment

Create a `.env` file in the project root:

```
GEMINI_TOKEN=your_google_gemini_api_key_here
```

### Run both services

```bash
# Terminal 1 — DaaS
python apiDaaS.py

# Terminal 2 — EaaS
python apiEaaS.py
```