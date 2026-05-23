from flask import Flask, request, jsonify
from llmCall import consultLlm


app = Flask(__name__)

# Helpers
def success_response(data):
    return jsonify({"status": "ok", "data": data}), 200


def error_response(message, code=400):
    return jsonify({"status": "error", "message": message, "code": code}), code


@app.get('/evaluate')
def evaluate():
    data = request.get_json(silent=True)
    if not data:
        return error_response("JSON body required")

    try:
        userId =int(data.get("userId"))
        poiId = int(data.get("poiId"))
        language = str(data.get("language"))
        allergies = bool(data.get("allergies"))
        context = str(data.get("context"))
    except (ValueError, TypeError) as e:
        return error_response(f"Invalid input: {str(e)}")

    # make all calls

    #return a json as said in the doc
    return success_response({"message": "Evaluation completed successfully"})

