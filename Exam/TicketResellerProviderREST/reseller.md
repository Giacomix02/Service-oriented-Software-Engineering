# Ticket Reseller

## Overview
The **Ticket Reseller** is a core **Provider** microservice within the GigSync SOA ecosystem. It simulates a secondary ticket market (resale platform) where users can find alternative tickets for music events.

This service is queried by the **Ticket Searcher (Prosumer)**, which consolidates data from both the *Legacy Box Office* (official prices) and this *Reseller* platform to offer users a comprehensive price comparison.

## Tech Stack
This service is built strictly following Jakarta EE specifications without relying on Spring Boot.

* **Language:** Java 17
* **Framework:** Jakarta EE (JAX-RS)
* **Implementation:** Apache CXF
* **Data Format:** JSON (via Jackson)
* **Database:** MySQL (JDBC Connector)
* **Deployment:** Docker & Apache Tomcat 11
* **API Documentation:** Swagger / OpenAPI 3

## API Endpoints
The base URL for all endpoints in this microservice is configured to `/api`.

### Get All Resale Tickets for an Event
Retrieves a list of all secondary market tickets available for a specific event.

* **URL:** `/api/getEventTickets/{Event_Global_ID}`
* **Method:** `GET`
* **Produces:** `application/json`

**Example Request:**
```http
GET http://localhost:9061/api/getEventTickets/9002
```

**Example Response:**
```json
[
  {
    "id": 101,
    "price": 250.00,
    "seat": "Section A, Row 5",
    "eventGlobalId": 9002
  },
  {
    "id": 102,
    "price": 180.50,
    "seat": "General Admission",
    "eventGlobalId": 9002
  }
]
```

### Get Specific Ticket Details
Retrieves exact details for a specific ticket, ensuring it belongs to the requested event.

* **URL:** `/api/getTicket/{Event_Global_ID}/{Ticket_ID}`
* **Method:** `GET`
* **Produces:** `application/json`

**Example Request:**
```http
GET http://localhost:9061/api/getTicket/9002/101
```

**Example Response:**
```json
{
  "id": 101,
  "price": 250.00,
  "seat": "Section A, Row 5",
  "eventGlobalId": 9002
}
```

## How to run
### Prerequisites
* Docker and Docker Compose installed
* Ensure the Eureka Discovery Server and API Gateway are running in your ecosystem.

### Setup isntructions
1. Compile the .war file manually using Maven
    ```bash
    mvn clean install
    ```

2. Build and run via Docker Compose
    ```bash
    docker-compose build ticket-reseller-microservice
    docker-compose up -d ticket-reseller-microservice
    ```