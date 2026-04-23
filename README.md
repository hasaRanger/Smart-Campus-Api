# Smart Campus Sensor & Room Management API 

## Project Overview

This project is a robust, scalable, and highly available **RESTful API** developed for the University's "Smart Campus" initiative. 

It serves as a backend infrastructure to manage thousands of Rooms and a diverse array of Sensors deployed across the campus. 

Built using the **JAX-RS** (Jakarta RESTful Web Services) framework, the API provides a seamless interface for facilities managers and automated systems to interact with campus environmental data.

---

## Project Folder Structure

The project follows a standard Maven web application structure:
```
smart-campus-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.smartcampus.smart_campus_api/   # Root package
│   │   │       ├── exception/                       # Custom runtime exceptions (e.g., RoomNotEmptyException)
│   │   │       │   └── mapper/                      # JAX-RS Exception Mappers → JSON error responses
│   │   │       ├── filter/                          # Request and response logging filters
│   │   │       ├── model/                           # POJOs: Room, Sensor, SensorReading
│   │   │       ├── resources/                       # JAX-RS resource classes (API endpoints)
│   │   │       ├── storage/                         # In-memory data layer (DataStore)
│   │   │       └── SmartCampusApp.java              # API configuration and entry point
│   │   └── webapp/
│   │       ├── WEB-INF/                             # web.xml and beans.xml (deployment config)
│   │       ├── META-INF/                            # context.xml (deployment path)
│   │       └── index.html                          # Default landing page
└── pom.xml                                         # Maven config: dependencies (Jersey, Jackson) & build settings
```
---

## Technical Architecture

| Component | Details |
|---|---|
| **Framework** | JAX-RS (Jersey implementation) |
| **Data Storage** | In-memory data structures (`ConcurrentHashMap` and Synchronized Lists) for thread safety |
| **Versioning** | All endpoints under `/api/v1` |

### Core Entities

- **Room** — Manages physical locations, human-readable names, and maximum occupancy for safety.
- **Sensor** — Tracks hardware category (e.g., `Temperature`, `CO2`), current state (`ACTIVE`, `MAINTENANCE`), and the most recent measurement.
- **SensorReading** — Maintains a historical log of measurement events, including UUID identifiers and epoch timestamps.

---

## Build and Launch Instructions

### Steps to Run

1. **Clone the Repository**
```bash
   git clone [Your-Public-GitHub-Repo-URL]
   cd smart-campus-api
```

2. **Build and Run**

   Choose your preferred setup method:

   ---

   #### 🖥️ Option A: NetBeans IDE

   **Prerequisites**
   - NetBeans IDE 12.0 or higher
   - Java Development Kit (JDK) 11
   - Apache Tomcat 9.0+ configured within NetBeans

   **Open Project**
   - Launch NetBeans IDE.
   - Go to `File > Open Project` and navigate to the cloned `smart-campus-api` folder.
   - NetBeans will automatically recognize it as a Maven project and resolve the structure.

   **Configure Tomcat** *(first-time setup only)*
   - Go to `Tools > Servers` and click **Add Server**.
   - Select **Apache Tomcat** and point it to your local Tomcat installation directory.
   - Complete the wizard and click **Finish**.

   **Clean and Build**
   - Right-click the project in the Projects pane and select **Clean and Build**.
   - Maven will download all dependencies (Jersey, Jackson) and package the app into `target/smart_campus_api.war`.

   **Run**
   - Right-click the project and select **Run**.
   - If prompted, select your configured Apache Tomcat server.
   - NetBeans will deploy the WAR and open your browser at `http://localhost:8080/smart_campus_api/`.

   ---

   #### ⌨️ Option B: Command Line (Maven + Tomcat)

   **Prerequisites**
   - Java Development Kit (JDK) 11
   - Apache Maven 3.6+
   - Apache Tomcat 9.0+

   **Build the WAR file**
```bash
   mvn clean package
```

   **Deploy to Tomcat**
   - Locate the generated `smart_campus_api.war` in the `target/` directory.
   - Copy it to the `webapps/` folder of your Tomcat installation:
```bash
     cp target/smart_campus_api.war /path/to/tomcat/webapps/
```

   **Start Tomcat**
```bash
   /path/to/tomcat/bin/startup.sh        # macOS/Linux
   /path/to/tomcat/bin/startup.bat       # Windows
```

   ---

3. **Verify the API**
   - Confirm the API is live by accessing the discovery endpoint:

     http://localhost:8080/smart_campus_api/api/v1

   - You should receive a JSON response containing API metadata and available resource links.

---

## HTTP Error Handling

The API is designed to be "leak-proof," returning structured JSON error responses instead of raw stack traces.

| HTTP Status | Error Type | Scenario | Response Hint |
|---|---|---|---|
| `403 Forbidden` | `SensorUnavailableException` | Posting readings to a sensor in `MAINTENANCE` status. | "Update the sensor status to 'ACTIVE' before posting new readings." |
| `409 Conflict` | `RoomNotEmptyException` | Deleting a room that still has sensors assigned to it. | "Reassign or delete all sensors in this room before decommissioning it." |
| `409 Conflict` | Duplicate ID | Creating a Room or Sensor with an ID that already exists. | *(Standard Conflict)* |
| `415 Unsupported Media Type` | `UnsupportedMediaTypeExceptionMapper` | Sending a request body with a `Content-Type` other than `application/json` (e.g., `text/plain`, `application/xml`). | "Set the request header: `Content-Type: application/json`" |
| `422 Unprocessable Entity` | `LinkedResourceNotFound` | Registering a sensor with a `roomId` that does not exist. | "Check that the 'roomId' field references a room that exists in the system." |
| `500 Internal Server Error` | `Throwable` (Global) | Any unexpected runtime error (e.g., `NullPointerException`). | "An unexpected error occurred. Please contact support or try again later." |

---

## Sample cURL Commands

### 1. Discovery Endpoint

```bash
curl -X GET http://localhost:8080/smart_campus_api/api/v1
```

### 2. Create a New Room

```bash
curl -X POST http://localhost:8080/smart_campus_api/api/v1/rooms \
     -H "Content-Type: application/json" \
     -d '{"id": "LIB-301", "name": "Library Quiet Study", "capacity": 50}'
```

### 3. Retrieve All Rooms

```bash
curl -X GET http://localhost:8080/smart_campus_api/api/v1/rooms
```

### 4. Register a Sensor

```bash
curl -X POST http://localhost:8080/smart_campus_api/api/v1/sensors \
     -H "Content-Type: application/json" \
     -d '{"id": "TEMP-001", "type": "Temperature", "status": "ACTIVE", "roomId": "LIB-301"}'
```

### 5. Filter Sensors by Type

```bash
curl -X GET "http://localhost:8080/smart_campus_api/api/v1/sensors?type=Temperature"
```

### 6. Add a Sensor Reading

```bash
curl -X POST http://localhost:8080/smart_campus_api/api/v1/sensors/TEMP-001/readings \
     -H "Content-Type: application/json" \
     -d '{"value": 22.5}'
```

### 7. Retrieve Sensor Reading History

```bash
curl -X GET http://localhost:8080/smart_campus_api/api/v1/sensors/SEN-001/readings
```

---
