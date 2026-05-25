from flask import Flask, request, jsonify
from flask_cors import CORS
from auditLogger import logEvent, getLog, newAudit
from decisionProcess import decide
import datetime
import requests


ipDaaS = "http://127.0.0.1:5000"

app = Flask(__name__)
CORS(app)

# Helpers
def success_response(data):
    return jsonify({"status": "ok", "data": data}), 200


def error_response(message, code=400):
    return jsonify({"status": "error", "message": message, "code": code}), code


@app.post('/evaluate')
def evaluate():
    data = request.get_json(silent=True)

    newAudit()
    logEvent("Received evaluation request in /api/evaluate with data from user")

    if not data:
        logEvent("No JSON body provided in the request")
        return error_response("JSON body required")

    try:
        poiId = int(data.get("poiId"))
        accessibility = bool(data.get("accessibility"))
        language = bool(data.get("language"))
        allergies = bool(data.get("allergies"))
        pollution = bool(data.get("pollution"))
        context = str(data.get("context"))
        visitDate = str(data.get("visitDate"))
    except (ValueError, TypeError) as e:
        return error_response(f"Invalid input: {str(e)}")


    logEvent("Validated input parameters for poiId: " + str(poiId))
    logEvent("Calling DaaS to get information about the Point Of Interest with id: " + str(poiId))

    try:
        daasResponse = requests.get(f"{ipDaaS}/pois/{poiId}")
        daasResponse.raise_for_status()
        poiData = daasResponse.json()
    except requests.RequestException as e:
        logEvent(f"Error calling DaaS for poiId {poiId}: {str(e)}")
        return error_response(f"Error calling DaaS: {str(e)}", 500)

    logEvent("Received response from DaaS for poiId: " + str(poiId))

    finalResponse = decide(selected_poi=poiData, accessibility_issue=accessibility, translate_language=language, overtourism=pollution, allergy=allergies, visitDate=visitDate, context=context)
    if finalResponse["error"] is not None:
        return error_response(finalResponse["error"], 500)


    audit = getLog()
    return success_response({"audit": audit, "decision": finalResponse, "poiId": poiId, "timestamp": str(datetime.datetime.now())})


if __name__ == "__main__":
    app.run(host="127.0.0.1",port=5001, debug=True)
