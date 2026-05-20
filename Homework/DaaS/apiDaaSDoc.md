### getById
*Method:* **GET**

*Param Format:* **URL Param**

*Params*:
- **id**:int

*Description:* extract the single POI with the specific id

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

*Description:* extract all POIs showing only vital infos

*Output:*

---

### getAll
*Method:* **GET**

*Param Format:* **No Param**

*Description:* extract all POIs showing all infos

*Output:*

---

### getByMunicipality
*Method:* **GET**

*Param Format:* **URL Param**

*Params*:
- **municipality**:string

*Description:* extract all POIs with given municipality

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
- **lat**:float
- **lon**:float

*Description:* find if there is a POI in this EXACT location

*Output:*

---

### getNearest
*Method:* **POST**

*Param Format:* **JSON**

*Params*:
- **lat**:float
- **lon**:float
- **delta**: float

*Description:* find if there is a POI nearby, using the delta

*Output:*

---

### getBySubject
*Method:* **GET**

*Param Format:* **URL Param**

*Params*:
- **subject**:string

*Description:* extract all POIs that contain corresponding subject

*Output:*


---

### getAllSubjects
*Method:* **GET**

*Param Format:* **No Param**

*Description:* extract all subjects from all POIs, can be used for GUI

*Output:*

---

### getByAdvancedSearch
*Method:* **POST**

*Param Format:* **JSON**

*Params*:
- **subject**:string
- **municipality**:string
- **keyword**: string
- **lat**: float
- **long**: float
- **delta**: float

*Description:* aggregation of all others endpoint with the add of feature of searching by keyword. The keyword is searched in all text field in all POIs.
All params are optional but at least we have to insert one; lant, long and delta are an atomic params 

*Output:*