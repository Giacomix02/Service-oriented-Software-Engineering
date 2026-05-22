import os
from dotenv import load_dotenv

class Env:
    _instance = None
    token = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(Env, cls).__new__(cls)
            load_dotenv()
            cls.token = os.getenv('GEMINI_TOKEN')
        return cls._instance

# here is the main core of the application
# llm token is putted in .env file

def consultLlm():
    env = Env()
    geminiToken = env.token



    return 0