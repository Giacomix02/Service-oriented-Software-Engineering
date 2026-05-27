from rdflib import Graph
from pathlib import Path # Import Path

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

            # --- MODIFICATION HERE ---
            # 1. Get the directory where this script (query.py) is located.
            script_dir = Path(__file__).resolve().parent

            # 2. Construct the full path to the RDF file relative to the script.
            rdf_file_path = script_dir / "attrattori.rdf"

            # 3. Parse the file using the absolute path.
            cls.g.parse(str(rdf_file_path))

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

