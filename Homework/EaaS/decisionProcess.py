import json

import google.genai.errors

from llmCall import consultLlm, AppliedPolicy
from auditLogger import logEvent

policies = None  # TODO


def assess_risk(policies) -> (
        int, list):
    risk_score = 0
    logEvent("calculating risk score based on triggered policies and their weights")
    triggered_policies = []
    for policy in policies:

        if (policy["policy_id"] == "pollen_allergies_policy" or
                policy["policy_id"] == "language_policy" or
                policy["policy_id"] == "accessibility_policy" or
                policy["policy_id"] == "overtourism_policy"):
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
    return {"audit_id": audit_id, "final_decision": final_decision, "risk_level": risk_level,
            "justification": justification,
            "required_action": required_actions}


def filterPolicies(policies:list, accessibility_issue: bool, translate_language: bool, overtourism: bool, allergy: bool) -> list:
    filtered_policies = []
    logEvent("Filtering policies based on user input and Point Of Interest characteristics")
    for policy in policies:
        if policy["policy_id"] == "pollen_allergies_policy" and allergy:
            filtered_policies.append(policy)
        if policy["policy_id"] == "language_policy" and translate_language:
            filtered_policies.append(policy)
        if policy["policy_id"] == "accessibility_policy" and accessibility_issue:
            filtered_policies.append(policy)
        if policy["policy_id"] == "overtourism_policy" and overtourism:
            filtered_policies.append(policy)
    logEvent("Filtered policies based on user input and Point Of Interest characteristics successfully, " + str(len(filtered_policies)) + " policies selected for evaluation")
    return filtered_policies


def decide(selected_poi: dict,
           accessibility_issue: bool, translate_language: bool, overtourism: bool, allergy: bool,
           visitDate: str, context: str = None) -> dict:
    try:
        logEvent("Loading policies from policies.json")
        with open("policies.json") as f:
            policies: list = json.load(f)
    except Exception as e:
        logEvent("Error loading policies from policies.json: " + str(e))
        raise e
    logEvent("Loaded policies from policies.json successfully")

    policiesFiltered = filterPolicies(policies, accessibility_issue, translate_language, overtourism, allergy)

    logEvent("Consulting LLM to evaluate policies for the selected Point Of Interest")
    try:
        llmReply: dict = consultLlm(policies=str(policiesFiltered), poi=str(selected_poi), visitDate=visitDate, context=context)
    except (google.genai.errors.ClientError, google.genai.errors.ServerError)  as e:
        logEvent("Google Gemini servers unreachable: " + e.response.text)
        return {"error": e.response.text}
    

    justification = llmReply["justification"]
    required_actions = llmReply["required_actions"]
    logEvent("Received response from LLM for policy evaluation successfully")

    reply_applied_policies: list[AppliedPolicy] = llmReply["applied_policies"]

    # policies_considered = [pol if policy["policy_id"] == pol["policy_id"] and policy["status"] == "triggered"
    #                        for policy in reply_applied_policies
    #                        for pol in policies]
    policies_considered = []
    for policy in reply_applied_policies:
        if policy["status"] == "triggered":
            sel_pol = None
            for pol in policies:
                if pol["policy_id"] == policy["policy_id"]:
                    sel_pol = pol
            if sel_pol is not None:
                policies_considered.append(sel_pol)

    risk_score, triggered_policies = assess_risk(policies_considered)

    final_decision, risk_level = make_decision(triggered_policies, risk_score)

    return packageDecision(llmReply["audit_id"], final_decision, risk_level, justification, required_actions)
