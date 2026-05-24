## RUN FLASK APP
> python apiEaaS.py

## ENDPOINT

### evaluate

>/evaluate

*Method:* **POST**

*Param Format:* **JSON**

*Params*:
- **poiId**: int
- **accessibility_issues**: boolean
- **language**: boolean (if the user want it in english or not)
- **allergy**: boolean
- **pollution**: boolean
- **context**: str (some other information from the user that can be useful for the evaluation, e.g: "I want to go to a natural park, but I have pollen allergy")
- **visitDate**: str

*Description:* receives a recommendation request, fetches the data from the DaaS, applies all active policies, computes the risk level and returns the decision with justification.

*Output:*
- JSON of
  - audit
  - decision
  - timestamp

## INTERNAL FUNCTIONS
### API Helpers (`apiEaaS.py`)

* **`success_response(data)`**: Wraps successful API outputs in a standard JSON format with a 200 HTTP status code.
* **`error_response(message, code)`**: Formats error messages into JSON and returns them with the specified HTTP status code.

### Audit Logging (`auditLogger.py`)

* **`newAudit()`**: Clears the current memory log and resets the file-writing flag for a new evaluation request.
* **`logEvent(event)`**: Appends a timestamped string to the current execution's log list in memory and writes it directly to the log file.
* **`getLog()`**: Joins all recorded log events from the current session into a single multiline string to be returned to the user.
* **`writeFile(log)`**: Handles file operations, creating or overwriting `audit_log.txt` on the first run of a session, and appending to it during subsequent calls.

### Decision Logic (`decisionProcess.py`)

* **`filterPolicies(policies, accessibility_issue, translate_language, overtourism, allergy)`**: Reduces the full list of system policies down to only those relevant to the user's explicit parameters (e.g., if the user has no allergies, the pollen policy is filtered out).
* **`assess_risk(policies)`**: Iterates through the policies the LLM marked as triggered, sums their `risk_weight` values to calculate a total risk score, and returns both the numeric score and the list of triggered policies.
* **`make_decision(policies, risk_score)`**: Translates the numerical risk score into a categorized risk level (`LOW`, `MEDIUM`, or `HIGH`) and determines the final decision (`PASS`, `REVISE`, or `ESCALATE`). It enforces a hard stop by overriding the final decision to `REJECT` if any single triggered policy mandates a rejection.
* **`packageDecision(...)`**: Formats the final verdict, risk level, justification, and required actions into a dictionary object for the API response.
* **`decide(...)`**: The main orchestrator function. It loads policies from `policies.json`, filters them, calls the LLM, matches the LLM's triggered policies back to the original definitions, calculates the risk score, makes the final decision, and packages everything.
* **`match_string_decision_to_priority(string)`**: A utility function that maps decision strings (`REVISE`, `REJECT`, `ESCALATE`) to numerical priority values.

### LLM Integration (`llmCall.py`)

* **`consultLlm(policies, poi, visitDate, context)`**: Initializes the Gemini client, constructs a targeted prompt using user data and system policies, and calls the model to fetch a structured JSON evaluation.

---

## POLICIES
### accessibility_policy
- *Decision*: **REVISE**
- *Trigger*: the POI has no accessibility information in the subject or description.
- *Required action*: If the user puts that he has accessibility issues, add a warning "Warning: Accessibility information missing." to that specific attraction before sending it to the user.
- *Risk weight*: 6
---

### Language policy
- *Decision*: **REVISE**
- *Trigger*: Content is only in Italian, but user prefers English.
- *Required action*: Translate the content to the desired language (EN) if the content is only in Italian.
- *Risk weight*: 3
---

### Overtourism/ pollution policy
- *Decision*: **REJECT** (for the especific recommendation) or **ESCALATE** (if the whole municple is collapsed)
- *Trigger*: High concentration of people causing pollution/overtourism.
- *Required action*: Reject the recommendation of an attraction or municiple if there are too many people in it, causing pollution and overtourism.
- *Risk weight*: 5
---

### Pollen/ allergies policy
- *Decision*: **REJECT**
- *Trigger*: User has a pollen allergy and the POI has a high pollen concentration
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
- compute the necessary policies to apply based on the user
- Send request to LLM to do the evaluation
  - LLM calls internal functions to:
    - assess risk
    - make decision
    - build justification
- fetch llm reply
- save the audit
- return the decision and justification to the user

## LLM Prompting
The system relies on the `gemma-4-31b-it` model to evaluate the Point of Interest (POI) data against the filtered policies.

### Structured Output Generation

The prompt forces the LLM to return a strict JSON structure defined by Pydantic schemas:

* **`EaaSDecision`**: The root schema requiring an `audit_id`, a `justification` for the overall decision, a list of `required_actions`, and a record of `applied_policies`.
* **`AppliedPolicy`**: A sub-schema detailing each evaluated policy, demanding a `policy_id`, a textual `reason` for the outcome, and a strict `status` output of either `"triggered"` or `"passed"`.

### Prompt Context and Injection

The prompt dynamically injects the following context to guide the LLM's reasoning:

- **User Parameters**: The specific `visitDate` and any extra `context` strings provided by the user.
- **System Data**: The dynamically filtered list of `policies` and the JSON string of `poi` details.
- **Reasoning Prompts**: The model is provided with explicit instructions on how to infer missing data:
  - *Pollens*: Instructs the model to infer potential issues by checking the specific month and considering historical or real-time pollen data if explicit POI info is missing.
  - *Accessibility*: Instructs the model to look for accessibility features in the description, or attempt to assume accessibility based on the nature of the place if direct information is omitted. *(Note: In the current code implementation, the accessibility reasoning prompt is injected twice, while the overtourism reasoning prompt is defined but not injected)*.