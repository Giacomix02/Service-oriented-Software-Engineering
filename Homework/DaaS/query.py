from rdflib import Graph

g = Graph()
g.parse("./tourpedia.rdf")

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

print(g.query(query))