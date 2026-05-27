# Run Flask API
> python apiDaaS.py

# API Documentation

### getById

>/pois/<int:poi_id>

*Method:* **GET**

*Param Format:* **URL Param**

*Params*:
- **id**: int

*Description:* extract the single POI with the specific id.

*Output:*
- array of
  - id
  - title
  - description
  - comment
  - short_description
  - municipality
  - lat
  - long
  - image

---

### getAllBasicInfos

>/pois/basic

*Method:* **GET**

*Param Format:* **No Param**

*Description:* extract all POIs showing only vital infos.

*Output:*
- array of
  - id
  - title
  - municipality
  - comment

---

### getAll

>/pois

*Method:* **GET**

*Param Format:* **No Param**

*Description:* extract all POIs showing all infos.

*Output:*
- array of
  - id
  - title
  - description
  - comment
  - short_description
  - municipality
  - lat
  - long
  - image

---

### getByMunicipality

>/pois/municipality/<string:municipality>

*Method:* **GET**

*Param Format:* **URL Param**

*Params*:
- **municipality**: string

*Description:* extract all POIs with given a municipality.

*Output:*
- array of
  - id
  - title
  - description
  - comment

---


### getMunicipalities

>/municipalities

*Method:* **GET**

*Param Format:* **No Param**

*Description:* extract all municipalities from all POIs, can be used for GUI.

*Output:*
- array of
  - municipality

### getByPosition

>/pois/position

*Method:* **POST**

*Param Format:* **JSON**

*Params*:
- **lat**: float
- **lon**: float

*Description:* find if there is a POI in this EXACT location.

*Output:*
- array of
  - id
  - title
  - description
  - lat
  - long
  - municipality
  - comment

---

### getNearest

>/pois/nearest

*Method:* **POST**

*Param Format:* **JSON**

*Params*:
- **lat**: float
- **lon**: float
- **delta**: float

*Description:* find if there is a POI nearby, using the delta.

*Output:*

---

### getBySubject

>/pois/subject/<string:subject>

*Method:* **GET**

*Param Format:* **URL Param**

*Params*:
- **subject**: string

*Description:* extract all POIs that contain corresponding subject.

*Output:*
- array of
  - id
  - title
  - description
  - subject
  - comment

---

### getAllSubjects

>/subjects

*Method:* **GET**

*Param Format:* **No Param**

*Description:* extract all subjects from all POIs, can be used for GUI.

*Output:*
- array of
  - subject

---

### getByKeyword

>/pois/keyword/<string:keyword>

*Method:* **GET**

*Param Format:* **URL Param**

*Params*:
- **keyword**: string

*Description:* extract all POIs that match the given keyword. The search is performed across all text fields: title, description, short_description, comment, and label (case-insensitive).

*Output:*
- array of
  - id
  - title
  - description
  - comment
  - short_description
  - municipality
  - lat
  - long
  - image

---

### getByAdvancedSearch (NOT IMPLEMENTED)

>/pois/search

*Method:* **POST**

*Param Format:* **JSON**

*Params*:
- **subject**: string
- **municipality**: string
- **keyword**: string
- **lat**: float
- **long**: float
- **delta**: float

*Description:* aggregation of all others endpoint with the add of feature of searching by keyword. The keyword is searched in all text field in all POIs.
All params are optional but at least we have to insert one; lat, long and delta are atomic params.

*Output:*