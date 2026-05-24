import os
from dotenv import load_dotenv
from google import genai
from pydantic import BaseModel, Field
from typing import Literal
from auditLogger import logEvent


class Env:
    _instance = None
    token = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(Env, cls).__new__(cls)
            load_dotenv()
            cls.token = os.getenv('GEMINI_TOKEN')
        return cls._instance


class AppliedPolicy(BaseModel):
    policy_id: str = Field(description="The unique identifier of the evaluated policy")
    reason: str = Field(description="A brief explanation of why the policy was triggered or passed based on the input data.")
    status: Literal["triggered", "passed"] = Field(description="The evaluation result of the policy.")


class EaaSDecision(BaseModel):
    audit_id: str = Field(description="Unique identifier for the audit record. Use the name and information about the Point Of Interest")
    # decision: Literal["PROCEED", "REVISE", "ESCALATE", "REJECT"] = Field(
    #     description="The final verdict computed by the policy engine."
    # )
    # risk_score: int = Field(description="The calculated numeric risk score (e.g., 100).")
    # risk_level: Literal["low", "medium", "high", "critical"] = Field(
    #     description="The readable risk classification."
    # )
    justification: str = Field(
        description="A short rationale explaining the decision, linked to the specific policy lens and context."
    )
    applied_policies: list[AppliedPolicy] = Field(
        description="A record of which external policies were evaluated and their status."
    )
    hard_policy_blocks: list[str] = Field(
        description="List of policy IDs that triggered a hard stop or rejection."
    )
    required_actions: list[str] = Field(
        description="Explicit instructions on what the system or human must do next based on triggered policies."
    )


promptReasoningOvertourism="If in the POI description you can't find infos about overturism and polluttion: this can be detected by checking the current month and the historical data of the number of visitors in that month, or by checking real-time data if available"
promptReasonPollens="If in the POI description you can't find infos about pollens: this can be detected by checking the current month and the historical data of the pollens in that month, or by checking real-time data if available"
promptReasonAccessibility="If in the POI description you can't find infos about accessibility: this can be detected by checking the presence of accessibility features in the POI description, or by checking real-time data or statica data if available. If you can't reason about the place and other infos and see if you can assume accessibility"

def consultLlm(policies: str,poi:str, visitDate:str) -> dict:
    env = Env()
    geminiToken = env.token

    if not geminiToken:
        logEvent("API token not found. Please check your .env file.")
        raise ValueError("API token not found. Please check your .env file.")


    # 3. Pass the key explicitly to the Client
    client = genai.Client(api_key=geminiToken)

    logEvent("Consulting LLM with prompt")

    if debug:
        with open('./geminiresponse.json') as f:
            return json.load(fp=f)
    else:
        response = client.models.generate_content(
            model='gemma-4-31b-it',
            contents=f"""You have to revise a choice of a Point Of Interest based of specific policies. The user want to visit the place at the date:{visitDate}. {promptReasonPollens}.{promptReasonAccessibility}.{promptReasonAccessibility}. Policies to check: {policies}. Infos about the place: {poi}. """,
            config={
                'response_mime_type': 'application/json',
                'response_schema': EaaSDecision,
            },
        )

    logEvent("LLM response received")
    with open('./geminiresponse.json', 'w') as f:
        json.dump(json.loads(response.text), f)
    return json.loads(response.text)

