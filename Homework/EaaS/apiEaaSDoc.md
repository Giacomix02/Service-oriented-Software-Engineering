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
- *Trigger*:
- *Required action*: Translate the content to the desired language (EN) if the user has a language preference and the content is only in Italian.
- *Risk weight*: ???
---

### Overtourism/ pollution policy
- *Decision*: **REJECT** (for the especific recommendation) or **ESCALATE** (if the whole municple is collapsed)
- *Trigger*:
- *Required action*: Reject the recommendation of an attraction or municiple if there are too many people in it, causing pollution and overtourism.
- *Risk weight*: ???
---

### Pollen/ allergies policy
- *Decision*: **REJECT**
- *Trigger*:
- *Required action*: If the user checks the "Pollen allergy" box, and there are places (e.g: Natural parks) that can have great concentration of pollen, the recommendation is rejected.
- *Risk weight*: ???