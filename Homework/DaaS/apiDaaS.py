from flask import Flask, request, jsonify
from flask_cors import CORS
from math import sqrt
from query import query
from rdflib import Literal, Namespace
from rdflib.namespace import XSD

app = Flask(__name__)
CORS(app)


@app.route("/")
def hello_world():
    return "<p>Hello, World! I'm working</p>"


# Helpers
def success_response(data):
    return jsonify({"status": "ok", "data": data}), 200


def error_response(message, code=400):
    return jsonify({"status": "error", "message": message, "code": code}), code


# --- Endpoints from apiDaaSDoc.md ---

# getAll - GET /pois
@app.get("/pois")
def get_all():
    # Return full POI objects
    prepared = """
        SELECT ?id ?title ?description ?comment ?short_description ?municipality ?lat ?long ?image
        WHERE {
          ?s umb:id ?id ;
             umb:titolo_testo ?title ;
             umb:testo ?description ;
             geo:lat ?lat ;
             geo:long ?long .
        
          OPTIONAL { ?s rdfs:comment ?comment . }
          OPTIONAL { ?s umb:descrizione_sintetica ?short_description . }
          OPTIONAL { ?s umb:immagine_copertina ?image . }
          OPTIONAL { ?s dbpedia-owl:municipality ?municipality . }
            FILTER(LANG(?comment) = "en")
          
          FILTER(LANG(?title) = "en" && LANG(?description) = "en")
        }"""

    try:
        # TODO: use bindings to avoid injection
        results = query(prepared, bindings={})  # No need for bindings?

        resultsJSON = []
        # DONE: transform results into a json
        for row in results:
            resultsJSON.append({
                "id": str(row.id) if row.id else None,
                "title": str(row.title) if row.title else None,
                "description": str(row.description) if row.description else None,
                "comment": str(row.comment) if row.comment else None,
                "short_description": str(row.short_description) if row.short_description else None,
                "municipality": str(row.municipality) if row.municipality else None,
                "lat": str(row.lat) if row.lat else None,
                "long": str(row.long) if row.long else None,
                "image": str(row.image) if row.image else None
            })

        return success_response(resultsJSON)

    except Exception as e:
        # Catch any errors from rdflib or the conversion process
        # Using 500 since this would be an internal server error / query failure
        return error_response(f"Failed to execute query or process results: {str(e)}", 500)


# getAllBasicInfos - GET /pois/basic
@app.get("/pois/basic")
def get_all_basic():
    # Only vital infos (id, name, municipality)
    prepared = """
        SELECT ?id ?title ?municipality ?comment
        WHERE {
            ?s umb:id ?id ;
            umb:titolo_testo ?title ;
            
            OPTIONAL { ?s rdfs:comment ?comment . }
            OPTIONAL { ?s dbpedia-owl:municipality ?municipality . }
            
            FILTER(LANG(?comment) = "en")
            FILTER(LANG(?title) = "en")
        }
        """

    try:
        # TODO: use bindings to avoid injection
        results = query(prepared, bindings={})

        resultsJSON = []
        for row in results:
            resultsJSON.append({
                "id": str(row.id) if row.id else None,
                "title": str(row.title) if row.title else None,
                "municipality": str(row.municipality) if row.municipality else None,
                "comment": str(row.comment) if getattr(row, "comment", None) else None
            })

        return success_response(resultsJSON)

    except Exception as e:
        # Catch any errors from rdflib or the conversion process
        # Using 500 since this would be an internal server error / query failure
        return error_response(f"Failed to execute query or process results: {str(e)}", 500)


# getById - GET /pois/<id>
@app.get("/pois/<int:poi_id>")
def get_by_id(poi_id):
    prepared = """
        SELECT ?id ?title ?description ?comment ?short_description ?municipality ?lat ?long ?image
        WHERE {
          ?s umb:id ?id ;
             umb:titolo_testo ?title ;
             umb:testo ?description ;
             geo:lat ?lat ;
             geo:long ?long .
        
          OPTIONAL { ?s rdfs:comment ?comment . }
          OPTIONAL { ?s umb:descrizione_sintetica ?short_description . }
          OPTIONAL { ?s umb:immagine_copertina ?image . }
          OPTIONAL { ?s dbpedia-owl:municipality ?municipality . }
          
FILTER(LANG(?comment) = "en")
          
          FILTER(LANG(?title) = "en" && LANG(?description) = "en")
          FILTER(STR(?id) = STR(?idGet))
        }"""

    try:
        # TODO: use bindings to avoid injection
        results = query(prepared, bindings={"idGet": poi_id})

        resultsJSON = []
        for row in results:
            resultsJSON.append({
                "id": str(row.id) if row.id else None,
                "title": str(row.title) if row.title else None,
                "description": str(row.description) if row.description else None,
                "comment": str(row.comment) if row.comment else None,
                "short_description": str(row.short_description) if row.short_description else None,
                "municipality": str(row.municipality) if row.municipality else None,
                "lat": str(row.lat) if row.lat else None,
                "long": str(row.long) if row.long else None,
                "image": str(row.image) if row.image else None
            })

        return success_response(resultsJSON)

    except Exception as e:
        # Catch any errors from rdflib or the conversion process
        # Using 500 since this would be an internal server error / query failure
        return error_response(f"Failed to execute query or process results: {str(e)}", 500)


# getByMunicipality - GET /pois/municipality/<municipality>
@app.get("/pois/municipality/<string:municipality>")
def get_by_municipality(municipality):
    prepared = """
        SELECT ?id ?title ?description ?comment
        WHERE {
          ?s dbpedia-owl:municipality ?m ;
             umb:id ?id ;
             umb:titolo_testo ?title ;
             umb:descrizione_sintetica ?description .
             
            OPTIONAL { ?s rdfs:comment ?comment . }
            

          FILTER(LANG(?title) = "en")
          FILTER(LANG(?comment) = "en") 
          FILTER(STR(?m) = STR(?municipalityName))
        }
        """

    try:
        results = query(prepared, bindings={"municipalityName": municipality})

        resultsJSON = []
        for row in results:
            resultsJSON.append({
                # Convert rdflib types (Literal, URIRef) to standard Python strings
                "id": str(row.id) if row.id else None,
                "title": str(row.title) if row.title else None,
                "description": str(row.description) if row.description else None,
                "comment": str(row.comment) if getattr(row, "comment", None) else None
            })

        return success_response(resultsJSON)

    except Exception as e:
        # Catch any errors from rdflib or the conversion process
        # Using 500 since this would be an internal server error / query failure
        return error_response(f"Failed to execute query or process results: {str(e)}", 500)


# getAllMunicipalities - GET /municipalities
@app.get("/municipalities")
def get_municipalities():
    prepared = """
        SELECT DISTINCT ?municipality
        WHERE {
          ?s dbpedia-owl:municipality ?municipality .
        }"""

    try:
        results = query(prepared, bindings={})

        municipalities = set()
        for row in results:
            if row.municipality:
                municipalities.add(str(row.municipality))

        resultsJSON = [{"municipality": m} for m in sorted(municipalities)]

        return success_response(resultsJSON)

    except Exception as e:
        return error_response(f"Failed to execute query or process results: {str(e)}", 500)


# getByPosition - POST /pois/position
@app.post("/pois/position")
def get_by_position():
    data = request.get_json(silent=True)
    if not data:
        return error_response("JSON body required")
    try:
        lat = float(data.get("lat"))
        long = float(data.get("long"))
    except (TypeError, ValueError):
        return error_response("lat and lon must be provided as numbers")

    prepared = """
        SELECT ?id ?title ?description ?municipality ?lat ?long ?comment
        WHERE {
          ?s umb:id ?id ;
             umb:titolo_testo ?title ;
             umb:testo ?description ;
             geo:lat ?lat ;
             geo:long ?long .
        
          OPTIONAL { ?s rdfs:comment ?comment . }
          OPTIONAL { ?s dbpedia-owl:municipality ?municipality . }
          
          FILTER(LANG(?comment) = "en")
          FILTER(LANG(?title) = "en" && LANG(?description) = "en")
          FILTER(STR(?lat) = STR(?latParam) && STR(?long) = STR(?longParam))
        }"""

    try:
        # TODO: use bindings to avoid injection
        results = query(prepared, bindings={
            "latParam": str(lat),
            "longParam": str(long)
        })

        resultsJSON = []
        for row in results:
            resultsJSON.append({
                "id": str(row.id) if row.id else None,
                "title": str(row.title) if row.title else None,
                "description": str(row.description) if row.description else None,
                "municipality": str(row.municipality) if row.municipality else None,
                "lat": str(row.lat) if row.lat else None,
                "long": str(row.long) if row.long else None,
                "comment": str(row.comment) if getattr(row, "comment", None) else None
            })

        return success_response(resultsJSON)

    except Exception as e:
        # Catch any errors from rdflib or the conversion process
        # Using 500 since this would be an internal server error / query failure
        return error_response(f"Failed to execute query or process results: {str(e)}", 500)


# getNearest - POST /pois/nearest
@app.post("/pois/nearest")
def get_nearest():
    data = request.get_json(silent=True)
    if not data:
        return error_response("JSON body required")
    try:
        lat = float(data.get("lat"))
        long = float(data.get("long"))
        delta = float(data.get("delta"))
    except (TypeError, ValueError):
        return error_response("lat, lon and delta must be numbers")

    if 0 > delta > 1:
        return error_response("delta must be a positive number, and less than 1 (since it's a degree difference)")
    else:
        delta = delta / 1000

    prepared = """
        
        
        SELECT ?id ?title ?description ?municipality ?lat ?long ?comment
        WHERE {
          ?s umb:id ?id ;
             umb:titolo_testo ?title ;
             umb:testo ?description ;
             geo:lat ?lat ;
             geo:long ?long .
        
          OPTIONAL { ?s dbpedia-owl:municipality ?municipality . }
          OPTIONAL { ?s rdfs:comment ?comment . }
          
            FILTER(LANG(?comment) = "en")
          FILTER(LANG(?title) = "en" && LANG(?description) = "en")
          FILTER(
            xsd:float(?lat) >= ?minLat && 
            xsd:float(?lat) <= ?maxLat && 
            xsd:float(?long) >= ?minLong && 
            xsd:float(?long) <= ?maxLong
          )
          
        }"""

    try:
        # TODO: use bindings to avoid injection
        results = query(prepared, bindings={
            "minLat": Literal(lat - delta, datatype=XSD.float),
            "maxLat": Literal(lat + delta, datatype=XSD.float),
            "minLong": Literal(long - delta, datatype=XSD.float),
            "maxLong": Literal(long + delta, datatype=XSD.float),
        })

        resultsJSON = []
        for row in results:
            resultsJSON.append({
                "id": str(row.id) if row.id else None,
                "title": str(row.title) if row.title else None,
                "description": str(row.description) if row.description else None,
                "municipality": str(row.municipality) if row.municipality else None,
                "lat": str(row.lat) if row.lat else None,
                "long": str(row.long) if row.long else None,
                "comment": str(row.comment) if getattr(row, "comment", None) else None
            })

        return success_response(resultsJSON)

    except Exception as e:
        # Catch any errors from rdflib or the conversion process
        # Using 500 since this would be an internal server error / query failure
        return error_response(f"Failed to execute query or process results: {str(e)}", 500)


# getBySubject - GET /pois/subject/<subject>
@app.get("/pois/subject/<string:subject>")
def get_by_subject(subject):
    prepared = """
        SELECT ?id ?title ?description ?subj ?comment
        WHERE {
          ?s dcterms:subject ?subj ;
             umb:id ?id ;
             umb:titolo_testo ?title ;
             umb:testo ?description ;
             
            OPTIONAL { ?s rdfs:comment ?comment . }
            
          FILTER(LANG(?comment) = "en")
          FILTER(LANG(?title) = "en" && LANG(?description) = "en")
          
          FILTER(CONTAINS(LCASE(STR(?subj)), LCASE(STR(?subjGet))))
        }"""

    try:
        # TODO: use bindings to avoid injection
        results = query(prepared, bindings={"subjGet": subject})

        resultsJSON = []
        for row in results:
            resultsJSON.append({
                # Convert rdflib types (Literal, URIRef) to standard Python strings
                "id": str(row.id) if row.id else None,
                "title": str(row.title) if row.title else None,
                "description": str(row.description) if row.description else None,
                "subject": str(row.subj) if row.subj else None,
                "comment": str(row.comment) if getattr(row, "comment", None) else None
            })

        return success_response(resultsJSON)

    except Exception as e:
        # Catch any errors from rdflib or the conversion process
        # Using 500 since this would be an internal server error / query failure
        return error_response(f"Failed to execute query or process results: {str(e)}", 500)


# getAllSubjects - GET /subjects
@app.get("/subjects")
def get_all_subjects():
    prepared = """
        SELECT DISTINCT ?subj
        WHERE {
          ?s dcterms:subject ?subj .
        }"""

    try:
        results = query(prepared, bindings={})

        unique_subjects = set()

        for row in results:
            if row.subj:
                subj_string = str(row.subj)

                for s in subj_string.split(','):
                    clean_subj = s.strip()

                    if clean_subj:
                        unique_subjects.add(clean_subj)

        resultsJSON = [{"subject": subj} for subj in sorted(unique_subjects)]

        return success_response(resultsJSON)

    except Exception as e:
        return error_response (f"Failed to execute query or process results: {str(e)}", 500)


# getByKeyword - GET /pois/keyword/<keyword>
@app.get("/pois/keyword/<string:keyword>")
def get_by_keyword(keyword):
    prepared = """
        SELECT ?id ?title ?description ?comment ?short_description ?municipality ?lat ?long ?image
        WHERE {
          ?s umb:id ?id ;
             umb:titolo_testo ?title ;
             umb:testo ?description ;
             geo:lat ?lat ;
             geo:long ?long .
        
          OPTIONAL { ?s rdfs:comment ?comment . }
          OPTIONAL { ?s umb:descrizione_sintetica ?short_description . }
          OPTIONAL { ?s umb:immagine_copertina ?image . }
          OPTIONAL { ?s dbpedia-owl:municipality ?municipality . }
          OPTIONAL { ?s rdfs:label ?label . }
          
          FILTER(LANG(?comment) = "en")
          FILTER(LANG(?title) = "en" && LANG(?description) = "en")
          FILTER(
            CONTAINS(LCASE(STR(?title)), LCASE(STR(?keywordParam))) ||
            CONTAINS(LCASE(STR(?description)), LCASE(STR(?keywordParam))) ||
            CONTAINS(LCASE(STR(?short_description)), LCASE(STR(?keywordParam))) ||
            CONTAINS(LCASE(STR(?comment)), LCASE(STR(?keywordParam))) ||
            CONTAINS(LCASE(STR(?label)), LCASE(STR(?keywordParam)))
          )
        }
        LIMIT 100"""

    try:
        results = query(prepared, bindings={"keywordParam": keyword})

        resultsJSON = []
        for row in results:
            resultsJSON.append({
                "id": str(row.id) if row.id else None,
                "title": str(row.title) if row.title else None,
                "description": str(row.description) if row.description else None,
                "comment": str(row.comment) if row.comment else None,
                "short_description": str(row.short_description) if row.short_description else None,
                "municipality": str(row.municipality) if row.municipality else None,
                "lat": str(row.lat) if row.lat else None,
                "long": str(row.long) if row.long else None,
                "image": str(row.image) if row.image else None
            })

        return success_response(resultsJSON)

    except Exception as e:
        return error_response(f"Failed to execute query or process results: {str(e)}", 500)


# # getByAdvancedSearch - POST /pois/search
# @app.post("/pois/search")
# def get_by_advanced_search():
#     data = request.get_json(silent=True) or {}
#     if not data:
#         return error_response("JSON body required")
#
#     subject = data.get("subject")
#     municipality = data.get("municipality")
#     keyword = data.get("keyword")
#     lat = data.get("lat")
#     lon = data.get("long") or data.get("lon")
#     delta = data.get("delta")
#
#     # this example is created by AI if we have a json file, take in consideration only to know more or less how the query should work
#     # results = SAMPLE_POIS
#     #
#     # if subject:
#     #     results = [p for p in results if subject.lower() in (s.lower() for s in p.get("subjects", []))]
#     # if municipality:
#     #     results = [p for p in results if p.get("municipality", "").lower() == municipality.lower()]
#     # if keyword:
#     #     k = keyword.lower()
#     #     results = [p for p in results if k in p.get("name", "").lower() or k in p.get("description", "").lower()]
#     # if lat is not None and lon is not None and delta is not None:
#     #     try:
#     #         lat = float(lat)
#     #         lon = float(lon)
#     #         delta = float(delta)
#     #         def dist(a,b):
#     #             return sqrt((a[0]-b[0])**2 + (a[1]-b[1])**2)
#     #         center = (lat, lon)
#     #         results = [p for p in results if dist((p.get("lat"), p.get("lon")), center) <= delta]
#     #     except (TypeError, ValueError):
#     #         return error_response("lat, long and delta must be numbers if provided")
#
#     prepared = """
#         """
#
#     try:
#         # TODO: use bindings to avoid injection
#         results = query(prepared, bindings={})
#
#         resultsJSON = []
#         # TODO: transform results into a json
#
#         return success_response(resultsJSON)
#
#     except Exception as e:
#         # Catch any errors from rdflib or the conversion process
#         # Using 500 since this would be an internal server error / query failure
#         return error_response(f"Failed to execute query or process results: {str(e)}", 500)


if __name__ == "__main__":
    app.run(host="127.0.0.1", port=5000, debug=True)
