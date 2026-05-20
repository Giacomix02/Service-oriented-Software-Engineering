from rdflib import Graph



SPARQL_PREFIXES = """
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX dcterms: <http://purl.org/dc/elements/1.1/>
PREFIX umb: <http://dati.regione.umbria.it/tourism/ontology/> 
PREFIX lgdo: <http://linkedgeodata.org/ontology/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
"""


class Dataset:
    _instance = None
    g = None
    def __new__(cls, *args, **kwargs):
        if cls._instance is None:
            cls._instance = super().__new__(cls)

            cls.g = Graph()
            cls.g.parse("./attrattori.rdf")

        return cls._instance



def query(query, bindings):
    ds = Dataset()
    prefixQuery = SPARQL_PREFIXES+query
    print(prefixQuery)
    print(bindings)
    results = ds.g.query(
        prefixQuery,
        initBindings=bindings
    )
    return results

