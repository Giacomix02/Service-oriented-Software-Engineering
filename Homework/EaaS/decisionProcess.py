import json
from enum import Enum

policies = None  # TODO


#
# class decisionsPriority(Enum):
#     REVISE = 1
#     REJECT = 2
#     ESCALATE = 3

def match_string_decision_to_priority(string: str):
    if string == "REVISE":
        return 1
    if string == "REJECT": 
        return 2
    if string == "ESCALATE":
        return 3
    return 0


def decide(selected_poi: dict, accessibility_issue: bool, translate_language: bool, allergy: bool):
    with open("policies.json") as f:
        policies: list = json.load(f)

    # TODO send reuquest to llm ?
    reply: dict = None

    reply_applied_policies: list = reply["applied_policies"]
    policies_considered = [policy if policy == policies[k]["policy_id"] else None
                           for policy in reply_applied_policies
                           for k in range(len(policies))
                           ]
    highest_priority_policy_considered = {"decision": 0}
    for policy in policies_considered:
        highest_priority_policy_considered = policy if match_string_decision_to_priority(policy["decision"]) > match_string_decision_to_priority(highest_priority_policy_considered["decision"]) else highest_priority_policy_considered


