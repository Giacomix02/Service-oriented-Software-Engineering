## ENDPOINT
### evaluate
*Method:* **POST**

*Param Format:* **JSON**

*Params*:
- **user_id**: int
- **poi_id**: int
- **language**: string (preferred language of the user: it, en)
- **allergy**: boolean
- **context**: str (optional: origin of the recommendation, nearest)

*Description:* receives a recommendation request, fetches the data from the DaaS, applies all active policies, computes the risk level and returns the decision with justification.

*Output:*
- JSON of
  - audit_id
  - decision
  - risk_level
  - justification
  - applied_policies
  - required_actions
  - timestamp

## INTERNAL FUNCTIONS
- *loadPolicies()*: loads all active policy definitions from external JSON files on disk.
- *fetchPoiData()*: calls the DaaS internally and retreives all the data of the POI being evaluated.
- *assessRisk(poi, policies, context)*: evaluates each active policy against the data and the context. Returns triggered policies and a computed risk score.
- *makeDecision(risk_score, triggered_policies)*: uses the risk score and triggered policies to reach a final decision.
- *buildJustification(triggered_policies, decision)*: generates a readable explanation of the decision.
- *saveAudit(evaluation)*: creates a record of the audit and adds it to the audit trail, and returns the audit_id.

## POLICIES
### accesibility_policy
- *Decision*: **REVISE**
- *Trigger*: the POI has no accesibility information in the subject or description.
- *Required action*: If the user puts that he has accesibility issues, add a warning "Warning: Accesibility information missing." to that specific attraction before sending it to the user.
- *Risk weight*: ???
---

### Language policy
- *Decision*: **REVISE**
- *Trigger*: Content is only in Italian, but user prefers English.
- *Required action*: Translate the content to the desired language (EN) if the user has a language preference and the content is only in Italian.
- *Risk weight*: 3
---

### Overtourism/ pollution policy
- *Decision*: **REJECT** (for the especific recommendation) or **ESCALATE** (if the whole municple is collapsed)
- *Trigger*: High concentration of people causing pollution/overtourism. This can be detected by checking the current month and the historical data of the number of visitors in that month, or by checking real-time data if available.
- *Required action*: Reject the recommendation of an attraction or municiple if there are too many people in it, causing pollution and overtourism.
- *Risk weight*: 4
---

### Pollen/ allergies policy
- *Decision*: **REJECT**
- *Trigger*: User has a pollen allergy and the POI has a high pollen concentration. This can be detected by checking the current month and the historical data of pollen concentration in that month, or by checking real-time data if available.
- *Required action*: If the user checks the "Pollen allergy" box, and there are places (e.g: Natural parks) that can have great concentration of pollen, the recommendation is rejected.
- *Risk weight*: 8

## Risk levels and weights:
- **LOW**: 0-3
- **MEDIUM**: 4-6
- **HIGH**: 7-10

## Pipeline:
- recive request from the user that contains:
  - Poi selected
  - accesibility issues
  - language preference
  - allergy information
- Send request to LLM to do the evaluation
- LLM calls internal functions to:
  - load policies
  - fetch data from DaaS
  - assess risk
  - make decision
  - build justification
  - save audit