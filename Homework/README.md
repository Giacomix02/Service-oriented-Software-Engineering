# SoSe - Attraction Evaluator & Finder

## Team Members
- Giacomo Paolocci
- Raffaele Lorusso
- Carla Rubio Espineira

---

## Index

- [Overview](#overview)
- [Architecture](#architecture)
- [Services](#services)
- [Evaluate Endpoint](#evaluate-endpoint)
- [Example Request](#example-requests)
- [Policies](#policies)
- [Pipeline](#pipeline)
- [LLM Integration](#llm-integration)
- [DaaS API Reference](#daas-api-reference)
- [AaaS API Reference](#eaas-api-reference)
- [Setup](#setup)


---
## Overview

**SoSe** is a two-service system for evaluating tourist Points of Interest (POIs) against a set of configurable policies. It combines a **Data-as-a-Service (DaaS)** layer for querying POI data from an RDF dataset, and an **Ethics-as-a-Service (EaaS)** layer that applies AI-powered policy checks to produce a risk-scored decision.

---

## Architecture

```
User Request ──── filter and request POIs ─────────────┐
     │                                                 │
     ▼                                                 ▼
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

## RDF Dataset

The dataset (`attrattori.rdf`) describes tourist attractions (POIs) in the Umbria region of Italy. It is structured using standard linked-data vocabularies.

### Namespaces Used

| Prefix | URI | Purpose |
|---|---|---|
| `rdf` | `http://www.w3.org/1999/02/22-rdf-syntax-ns#` | RDF core types |
| `rdfs` | `http://www.w3.org/2000/01/rdf-schema#` | Labels, comments |
| `geo` | `http://www.w3.org/2003/01/geo/wgs84_pos#` | Latitude / longitude |
| `dcterms` | `http://purl.org/dc/elements/1.1/` | Title, description, subject |
| `umb` | `http://dati.regione.umbria.it/tourism/ontology/` | Domain-specific properties |
| `lgdo` | `http://linkedgeodata.org/ontology/` | Geo-linked data types |

### Main Properties per POI

| Property | Description |
|---|---|
| `dcterms:title` | Name of the attraction |
| `dcterms:description` | Full description (typically in Italian) |
| `dcterms:subject` | Category tags (e.g. "Borgo antico", "Parco naturale") |
| `rdfs:comment` | Short description / notes |
| `geo:lat` / `geo:long` | GPS coordinates |
| `umb:municipality` | Municipality the POI belongs to |
| `umb:image` | URL of a representative image |

### Example SPARQL Queries

**Get a POI by ID:**
```sparql
SELECT ?title ?description ?lat ?long
WHERE {
  ?poi dcterms:identifier "5033571"^^xsd:integer ;
       dcterms:title ?title ;
       dcterms:description ?description ;
       geo:lat ?lat ;
       geo:long ?long .
}
```

**Get all POIs in a municipality:**
```sparql
SELECT ?id ?title ?description
WHERE {
  ?poi umb:municipality ?mun ;
       dcterms:identifier ?id ;
       dcterms:title ?title ;
       dcterms:description ?description .
  FILTER(LCASE(STR(?mun)) = LCASE("Todi"))
}
```

**Find nearest POI within a bounding box (multi-condition query):**
```sparql
SELECT ?id ?title ?lat ?long
WHERE {
  ?poi geo:lat ?lat ;
       geo:long ?long ;
       dcterms:identifier ?id ;
       dcterms:title ?title .
  FILTER(?lat >= ?minLat && ?lat <= ?maxLat &&
         ?long >= ?minLong && ?long <= ?maxLong)
}
```

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

## Example Requests

### Example 1 — Result: PASS / REVISE

A user visiting a historic village in November, who prefers English content but has no other concerns.

**Request:**
```json
{
  "poiId": 5033571,
  "accessibility": false,
  "language": true,
  "allergies": false,
  "pollution": false,
  "context": "",
  "visitDate": "10/11/2026"
}
```

**Response (abbreviated):**
```json
{
  "decision": {
    "audit_id": "agliano_ancient_village",
    "final_decision": "PASS",
    "risk_level": "LOW",
    "justification": "The POI description is only available in Italian, triggering the language policy. No other policies apply. Risk score: 3.",
    "required_action": ["Translate the POI description from Italian to English."]
  }
}
```

**Why:** Only the language policy is triggered (risk weight 3), resulting in a LOW risk score and a PASS decision with a recommended translation action.

---

### Example 2 — Result: ESCALATE / REJECT

A user with a pollen allergy and accessibility needs planning a summer visit to a natural park.

**Request:**
```json
{
  "poiId": 5041892,
  "accessibility": true,
  "language": true,
  "allergies": true,
  "pollution": true,
  "context": "I use a wheelchair and have severe hay fever",
  "visitDate": "15/06/2026"
}
```

**Response (abbreviated):**
```json
{
  "decision": {
    "audit_id": "monte_subasio_natural_park",
    "final_decision": "REJECT",
    "risk_level": "HIGH",
    "justification": "The POI is a natural park with unpaved trails, presenting significant accessibility barriers for wheelchair users. June is peak pollen season in Umbria, making this visit high-risk for someone with severe hay fever. The pollen allergy policy mandates rejection.",
    "required_action": [
      "Do not recommend this POI to this user.",
      "Suggest accessible indoor alternatives in the same municipality.",
      "Translate the POI description to English."
    ]
  }
}
```

**Why:** The pollen policy (risk weight 8) triggers a hard REJECT override regardless of the total score. Additionally, the accessibility policy (weight 6) and language policy (weight 3) are triggered, bringing the total risk score to 17 — HIGH level.

---

## Audit Trail

Every evaluation produces a timestamped audit log stored in `audit_log.txt` and returned inline in the API response. The log traces the full evaluation pipeline.

### Example Audit Record

```
2026-05-24 23:24:54.745174: Received evaluation request in /api/evaluate with data from user
2026-05-24 23:24:54.746201: Validated input parameters for poiId: 5033571
2026-05-24 23:24:54.746890: Calling DaaS to get information about the Point Of Interest with id: 5033571
2026-05-24 23:24:54.812345: Received response from DaaS for poiId: 5033571
2026-05-24 23:24:54.813001: Loading policies from policies.json
2026-05-24 23:24:54.813512: Loaded policies from policies.json successfully
2026-05-24 23:24:54.814003: Filtering policies based on user input and Point Of Interest characteristics...
2026-05-24 23:24:54.814201: the user requested: Accessibility issue:False Translate language:True Overtourism:False Allergy:False
2026-05-24 23:24:54.814891: Language policy added
2026-05-24 23:24:54.815100: Filtered policies successfully, 1 policies selected for evaluation
2026-05-24 23:24:54.815300: Consulting LLM to evaluate policies for the selected Point Of Interest
2026-05-24 23:24:58.921044: LLM response received
2026-05-24 23:24:58.922100: calculating risk score based on triggered policies and their weights
2026-05-24 23:24:58.922400: policy triggered: language_policy with risk weight 3
2026-05-24 23:24:58.922600: risk_score is 3
2026-05-24 23:24:58.922800: risk level is LOW
2026-05-24 23:24:58.923000: Decision is to PASS
```

The audit log is both written to `audit_log.txt` on disk and returned in the API response under the `audit` field, ensuring full provenance and traceability of every decision.

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

### `policies.json` Structure

```json
[
  {
    "policy_id": "accessibility_policy",
    "decision": "REVISE",
    "risk_weight": 6,
    "description": "Triggered when the POI has no accessibility information and the user has declared accessibility needs.",
    "required_action": "Add a warning: 'Accessibility information missing for this attraction.'"
  },
  {
    "policy_id": "language_policy",
    "decision": "REVISE",
    "risk_weight": 3,
    "description": "Triggered when content is only in Italian but the user prefers English.",
    "required_action": "Translate the POI description from Italian to English."
  },
  {
    "policy_id": "overtourism_pollution_policy",
    "decision": "REJECT",
    "risk_weight": 5,
    "description": "Triggered when high visitor concentration is causing overtourism or environmental pollution.",
    "required_action": "Reject recommendation of this attraction or escalate if the whole municipality is affected."
  },
  {
    "policy_id": "pollen_allergies_policy",
    "decision": "REJECT",
    "risk_weight": 8,
    "description": "Triggered when the user has a pollen allergy and the POI has high pollen concentration (e.g. natural parks in spring/summer).",
    "required_action": "Do not recommend this POI. Suggest alternatives with lower pollen exposure."
  }
]
```

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

Full documentation: [`apiDaaSDoc.md`](./DaaS/apiDaaSDoc.md)

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
| `/pois/keyword/<keyword>` | GET | Full-text search across all POI fields |

---

## EaaS API Reference

Full documentation: [`apiEaaSDoc.md`](./EaaS/apiEaaSDoc.md)

| Endpoint | Method | Description |
|---|---|---|
| `/evaluate` | POST | Submit a POI for policy evaluation |

---

## Setup

### Requirements

```bash
pip install flask flask-cors requests google-generativeai python-dotenv rdflib pydantic
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

### Run the frontend

```bash
npm install
npm run dev
```

Open `http://localhost:5173` in your browser. Both DaaS (port 5000) and EaaS (port 5001) must be running before using the app.