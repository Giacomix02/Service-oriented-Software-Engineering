# Run Flask API
>flask --app main run

# API Documentation
### getById
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
*Method:* **GET**

*Param Format:* **No Param**

*Description:* extract all POIs showing only vital infos.

*Output:*
- array of
  - id
  - title
  - municipality

---

### getAll
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

---

### getByPosition
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

---

### getNearest
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

---

### getAllSubjects
*Method:* **GET**

*Param Format:* **No Param**

*Description:* extract all subjects from all POIs, can be used for GUI.

*Output:*
- array of
  - subject

---

### getByAdvancedSearch
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