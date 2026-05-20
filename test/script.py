from rdflib import Graph

g = Graph()
g.parse(location="Service-oriented-Software-Engineering/tourpedia.rdf", format="application/rdf+xml")


print(len(g))

query = f"""
PREFIX dbp: <http://dbpedia.org/ontology/>
PREFIX sch: <https://schema.org/>

SELECT *
WHERE {{
    ?sub ?pred ?obj .
}}
LIMIT 10
"""

result = g.query(query)

result.serialize(destination="results.txt", format="txt")