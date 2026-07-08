# GigSync

## USED TECHNOLOGIES
### Spring Boot
- **Java version**: 17
- **Spring Boot version**: 4.0.6

### Apache CXF
- **Java version**:

### Jakarta
- **Java version**:


## ARCHITECTURAL COMPONENTS
| ID | COMPONENT            | ROLE                  | TECHNOLOGY        | DESCRIPTION                                                                                           |
|----|----------------------|-----------------------|-------------------|-------------------------------------------------------------------------------------------------------|
| 1  | API Gateway          | **Gateway**           | Spring Cloud      | Acts as the single entry point for all client-to-service interactions. Built with Spring Cloud Gateway |
| 2  | Ticket Searcher      | **Prosumer**          | Spring (REST)     | It queries both ticket providers (4,5)                                                                |
| 3  | Artist Analyzer      | **Prosumer**          | Spring (REST)     | Fetches the artist's trending tracks and overall information (6,7)                                    |
| 4  | Legacy box office    | **Provider**          | Apache CXF (SOAP) | A service exposing a SOAP interface returning official prices                                         |
| 5  | Reseller             | **Provider**          | Jakarta (REST)    | A service returning secondary market prices                                                           |
| 6  | Music stats          | **Provider/Prosumer** | Spring (REST)     | A service returning all info about the artist and his songs                                           |
| 7  | Streaming Aviability | **Provider**          | Spring (REST)     | A service that returns if a song is aviable inside n streaming services                               |
| 8  | Load Balancer        | **Load Balancer**     | Spring Eureka     | Load balancer                                                                                         |


## ARCHITECTURE DIAGRAM
![](./img/architecture_diagram.png)

## DB DIAGRAM

> DB diagram for:
> - Music stats


![](./img/musicStatsDB.png)

---

> DB diagram for:
> - Legacy box office
> - Reseller

![](./img/ticketDB.png)


> DB diagram for:
> - Streaming Availability

![](./img/Streaming.png)


### Legacy Box Office and Reseller

#### Event

| Column Name | Data Type |
| --- | --- |
| Event_Global_ID | int |
| Name | str |
| Artist Name | str |
| Location | str |
| Description | str |

#### Ticket

| Column Name | Data Type |
| --- | --- |
| ID | int |
| Price | float |
| Seat | str |
| Event_Global_ID | int |

> **Ticket ID:** unique for each event, so same ID can't be in reseller and legacy box office
 
> **Event_Global_ID:** unique, in different DBs the same event has the same ID.

> **Relationship Note:** `Event_Global_ID` in the **Ticket** table has a many-to-one relationship (`0,n` to `1,1`) with the `Event_Global_ID` column in the **Event** table.

---

### Music Stats

#### artist

| Column Name | Data Type |
|-------------| --- |
| id          | int |
| name        | str |
| description | str |

#### song

| Column Name | Data Type |
|-------------| --- |
| id          | int |
| name        | str |
| description | str |
| views       | int |
| artist_id   | int |

> **Song ID:** unique, in different DBs the same song has the same ID. For example the Streaming and Music Stats DBs.

> **Relationship Note:** `artist_id` in the **Song** table has a many-to-one relationship (`0,n` to `1,1`) with the `id` column in the **Artist** table. 

---

### Streaming Availability

#### streaming_service

| Column Name | Data Type |
|-------------| --- |
| id          | int |
| name        | str |
| description | str |


#### availability

| Column Name          | Data Type |
|----------------------| --- |
| id                   | int |
| id_song              | int |
| is_streaming_service | int |


#### song

| Column Name | Data Type |
|-------------| --- |
| id          | int |

> **Relationship Note:** The **Availability** table serves as a junction table to establish a many-to-many relationship between **Streaming_Service** and **Song**.
> * `id_streaming_service` in the **Availability** table has a many-to-one relationship (`0,n` to `1,n`) with the `id` column in the **Streaming_Service** table.
> * `id_song` in the **Availability** table has a many-to-one relationship (`0,n` to `1,n`) with the `id` column in the **Song** table.

## ENDPOINTS

### 1: Gateway

#### Swagger UI: http://localhost:8080/swagger-ui/index.html

#### Endpoints

>These are the endpoins visible by the user via the gateway

| INVOLVED MICROSERVICES | URL                                   | METHOD | DESCRIPTION                                                     |
|------------------------|---------------------------------------|--------|-----------------------------------------------------------------|
|                        | events/getEventByID/{Event_Global_ID} | GET    | Get all infos of an event by its ID                             |
|                        | events/getAllEvents                   | GET    | Get all aviable events                                          |
|                        | events/searchByName/{Name}            | GET    | Get all aviable events by the Name                              |
| ...........            | .............................         | ...    | .............................                                   |
| 3, 6, 7                | /api/analyze/streaming-services       | GET    | Get all aviable streaming services                              |
| 3, 6, 7                | /api/analyze/songs                    | GET    | Get all songs with associated streaming services                |
| 3, 6, 7                | /api/analyze/songs/{id}               | GET    | Get a song by ID with its associated streaming services         |
| 3, 6, 7                | /api/analyze/songs/by-name/{name}     | GET    | Get a song by Name with its associated streaming services       |
| 3, 6, 7                | /api/analyze/songs/by-artist/{name}   | GET    | Get all artist's songs with their associated streaming services |
| 3, 6                   | /api/analyze/artists                  | GET    | Get all artists                                                 |
| 3, 6                   | /api/analyze/artists/{id}             | GET    | Get artist by his ID                                            |
| ...........            | .............................         | ...    | .............................                                   |
| 6                      | /api/stats/artists                    | GET    | Get all artists                                                 |
| 6                      | /api/stats/artists/{id}               | GET    | Get artist by his ID                                            |
| 6                      | /api/stats/songs                      | GET    | Get all songs                                                   |
| 6                      | /api/stats/songs/{id}                 | GET    | Get a song by ID                                                |
| 6                      | /api/stats/songs/by-name/{name}       | GET    | Get a song by its Name                                          |
| 6                      | /api/stats/songs/by-artist/{name}     | GET    | Get all songs by artist Name                                    |
| 6                      | /api/stats/artists/by-name/{name}     | GET    | Get an artist by Name                                           |



### 2: Ticket Searcher
| ID | URL                                     | METHOD    | DESCRIPTION                                                                                                                                  |
|----|-----------------------------------------|-----------|----------------------------------------------------------------------------------------------------------------------------------------------|
| 1  | getEventByID/{Event_Global_ID}          | GET       | Get all infos of an event directly from Legacy box office                                                                                    |
| 2  | getAllEvents                            | GET       | Get all aviable events directly from Legacy box office                                                                                       |
| 3  | searchByName/{Name}                     | GET       | Get all aviable events by the Name directly from Legacy box office                                                                           |
| 4  | getEventTickets/{Event_Global_ID}       | GET       | Get all aviable tickets by the Event ID by querying both Legacy box office and resellers                                                     |
| 5  | getTicket/{Event_Global_ID}/{Ticket_ID} | GET/POST? | Get Ticket info by his ID and the Event. Fetching it from either the Legacy box office or resellers  **IF IS A POST WE HAVE TO SEND A JSON** |

### 3: Artist Analyzer (/artist-analyzer/)
| ID | URL                    | METHOD | DESCRIPTION                          |
|----|------------------------|--------|--------------------------------------|
| 1  | artists                | GET    | Get all artists                      |
| 2  | songs                  | GET    | Get all songs                        |
| 3  | songs/{id}             | GET    | Get a song by its id                 |
| 4  | songs/by-name/{name}   | GET    | Get songs by name                    |
| 5  | songs/by-artist/{name} | GET    | Get songs by their artist's name     |
| 6  | streaming-services     | GET    | Get all available streaming services |
| 7  | artists/{id}           | GET    | Get artist by its id                 |


### 4: Legacy box office
| ID | URL                                            | METHOD    | DESCRIPTION                                                                      |
|----|------------------------------------------------|-----------|----------------------------------------------------------------------------------|
| 1  | getEventByID/{Event_Global_ID}          | GET       | Get all infos of an event by its ID                                              |
| 2  | getAllEvents                            | GET       | Get all aviable events                                                           |
| 3  | searchByName/{Name}                     | GET       | Get all aviable events by the Name                                               |
| 4  | getEventTickets/{Event_Global_ID}       | GET       | Get all aviable tickets by the Event ID                                          |
| 5  | getTicket/{Event_Global_ID}/{Ticket_ID} | GET/POST? | Get Ticket info by his ID and the Event. **IF IS A POST WE HAVE TO SEND A JSON** |

### 5: Reseller
| ID | URL                                            | METHOD    | DESCRIPTION                                                                      |
|----|------------------------------------------------|-----------|----------------------------------------------------------------------------------|
| 1  | getEventTickets/{Event_Global_ID}       | GET       | Get all aviable tickets by the Event ID                                          |
| 2  | getTicket/{Event_Global_ID}/{Ticket_ID} | GET/POST? | Get Ticket info by his ID and the Event. **IF IS A POST WE HAVE TO SEND A JSON** |

### 6: Music stats (/)
| ID | URL                           | METHOD | DESCRIPTION                          |
|----|-------------------------------|--------|--------------------------------------|                                         
| 1  | songs                         | GET    | Get all songs                        |
| 2  | songs/by-name/{Song_Name}     | GET    | Search and get a Song by its Name    |
| 3  | songs/by-artist/{Artist_Name} | GET    | Search and get a Song by its Name    |
| 4  | artists                       | GET    | Get all artists                      |
| 5  | artists/{id}                  | GET    | Get an artist by their id            |
| 6  | artists/by-name/{Artist_Name} | GET    | Search and get an Artist by his Name |
| 7  | songs/{id}                    | GET    | get a song by its id                 |


### 7: Streaming Aviability (/streaming-availability/)
| ID | URL                                    | METHOD | DESCRIPTION                                                       |
|----|----------------------------------------|--------|-------------------------------------------------------------------|
| 1  | get-song-availability/{Song_ID}        | GET    | Gets streaming availability for all songs of the requested artist |
| 2  | get-all-songs-for-service/{Service_ID} | GET    | Get all songs aviable for a specific streaming service            |
| 3  | get-all-streaming-services             | GET    | Gets all streaming services aviable in the system                 |

## ASYNCHRONOUS COMMUNICATION

**The asyncronus communication is implemented inside:**

- **Ticket Provider** that has to call two different services (4,5) to get the price of the tickets and get the aviable events.
- **Artist Analyzer** that has to call two different services (6,7) to get the artist's songs and their streaming availability.

## INTERACTING SCENARIOS

![](./img/stats.png)

![](./img/analyze.png)