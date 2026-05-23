import os
from dotenv import load_dotenv
from google import genai
from pydantic import BaseModel, Field
from typing import Literal


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
    policy_id: str = Field(description="The unique identifier of the evaluated policy (e.g., 'pollen_allergies_policy_v1').")
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


def consultLlm(prompt: str, date:str):
    env = Env()
    geminiToken = env.token

    if not geminiToken:
        raise ValueError("API token not found. Please check your .env file.")

    client = genai.Client()
    # 3. Pass the key explicitly to the Client
    client = genai.Client(api_key=geminiToken)

    response = client.models.generate_content(
        model='gemma-4-31b-it',
        contents=f"""You have to revise a choice of a Point Of Interest based of specific policies. The user want to visit the place at the date:{date}. Main informations: {prompt}""",
        config={
            'response_mime_type': 'application/json',
            'response_schema': EaaSDecision,
        },
    )

    return response.text

