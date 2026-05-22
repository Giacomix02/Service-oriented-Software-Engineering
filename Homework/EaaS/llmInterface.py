import os
from dotenv import load_dotenv


# here is the main core of the application
# llm token is putted in .env file
# to load env:
#
# load_dotenv()
# GEMINI_TOKEN = os.getenv('GEMINI_TOKEN')
# probably for the env is better to create a singleton to not load it every time

def consultLlm():

    return 0