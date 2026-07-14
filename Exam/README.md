### 1. Git Clone the Repository
git clone https://github.com/Giacomix02/Service-oriented-Software-Engineering

### 2. Navigate to the Exam Directory
Change your working directory to the target application folder:
```bash
cd ./Service-oriented-Software-Engineering/Exam
```

### 3. Build and Launch the Application
Depending on your operating system, choose **Option A** or **Option B** below:

#### Option A: Windows (Automatic Setup)
Simply run the pre-configured batch file in your terminal or double-click it in File Explorer:

```cmd
"Build everything and launch docker compose.bat"
```

#### Option B: Unix / macOS / Linux (Manual Setup)
If you are on a non-Windows machine, you must manually package the Maven projects and start Docker Compose.

1. Package each Maven microservice (skip tests to speed up the process). Execute this command inside each service directory (excluding folders like `DB`, `courseSlides`, `doc`, and `frontend`):
   ```bash
   ./mvnw package -DskipTests
   ```
2. Return to the `Exam` root directory and launch the Docker containers:
   ```bash
   docker compose up --build
   ```

---

## Accessing the Platform

Once the console logs stabilize and the services are active, you can access the application via the following interfaces:

| Component | URL / Address | Description |
| :--- | :--- | :--- |
| **Frontend Web App** | [http://localhost:3000/](http://localhost:3000/) | The main user interface built with Next.js and React|
| **API Gateway Swagger Hub** | [http://localhost:9000/swagger-ui/index.html](http://localhost:9000/swagger-ui/index.html) | Consolidates all microservice REST API endpoint documentations in one interface |
| **Discovery Service (Eureka)** | [http://localhost:8761](http://localhost:8761) | The Spring Eureka load balancer dashboard tracking active microservices |

---