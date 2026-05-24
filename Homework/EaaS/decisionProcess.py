import json
from llmCall import consultLlm, AppliedPolicy
from auditLogger import logEvent

policies = None  # TODO



def match_string_decision_to_priority(string: str):
    if string == "REVISE":
        return 1
    if string == "REJECT": 
        return 2
    if string == "ESCALATE":
        return 3
    return 0


def assess_risk(policies, overtourism: bool, accessibility_issue: bool, translate_language: bool, allergy: bool) -> (
        int, list):
    risk_score = 0
    logEvent("calculating risk score based on triggered policies and their weights")
    triggered_policies = []
    for policy in policies:

        if (allergy and policy["policy_id"] == "pollen_allergies_policy" or
                translate_language and policy["policy_id"] == "language_policy" or
                accessibility_issue and policy["policy_id"] == "accessibility_policy" or
                overtourism and policy["policy_id"] == "overtourism_policy"):
            risk_score += int(policy["risk_weight"])
            triggered_policies.append(policy)
            logEvent("policy triggered:" + str(policy["policy_id"]) + " with risk weight " + str(policy["risk_weight"]))
    logEvent("risk_score is " + str(risk_score))
    return risk_score, triggered_policies


def make_decision(policies: dict, risk_score: int) -> (str, str):
    final_decision: str = "PASS"
    risk_level: str = None
    if risk_score < 0:
        logEvent("warning, risk_score is below zero unexpectedly (decisionProcess.py make_decision())")
    if 0 <= risk_score <= 3:
        risk_level = "LOW"
    if 4 <= risk_score <= 6:
        risk_level = "MEDIUM"
    if 7 <= risk_score:
        risk_level = "HIGH"

    logEvent("risk level is " + str(risk_level))
    if risk_level == "MEDIUM":
        final_decision = "REVISE"
    if risk_level == "HIGH":
        final_decision = "ESCALATE"

    if any([policy["decision"] == "REJECT" for policy in policies]):
        final_decision = "REJECT"
    logEvent("Decision is to" + final_decision)
    return final_decision, risk_level


def packageDecision(audit_id, final_decision, risk_level, justification, required_actions) -> dict:
    return {"audit_id": audit_id, "final_decision": final_decision, "risk_level": risk_level, "justification": justification,
                       "required_action": required_actions}


def decide(selected_poi: dict,
           accessibility_issue: bool, translate_language: bool, overtourism: bool, allergy: bool,
           visitDate: str) -> dict:
    try:
        logEvent("Loading policies from policies.json")
        with open("policies.json") as f:
            policies: list = json.load(f)
    except Exception as e:
        logEvent("Error loading policies from policies.json: " + str(e))
        raise e
    logEvent("Loaded policies from policies.json successfully")

    logEvent("Consulting LLM to evaluate policies for the selected Point Of Interest")
    llmReply:dict = consultLlm(policies=str(policies), poi=str(selected_poi), visitDate=visitDate)
    justification = llmReply["justification"]
    required_actions = llmReply["required_actions"]
    logEvent("Received response from LLM for policy evaluation successfully")

    reply_applied_policies: list[AppliedPolicy] = llmReply["applied_policies"]

    policies_considered = [pol if policy["policy_id"] == pol["policy_id"] and policy["status"] == "triggered" else None
                           for policy in reply_applied_policies
                           for k in range(len(policies))
                           ]
    highest_priority_policy_considered = {"decision": 0}
    for policy in policies_considered:
        highest_priority_policy_considered = policy if match_string_decision_to_priority(policy["decision"]) > match_string_decision_to_priority(highest_priority_policy_considered["decision"]) else highest_priority_policy_considered


