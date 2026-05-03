# High-Availability Stock Market Simulator

A simplified, robust REST API for simulating a stock market. Built with Java 21, Spring Boot 4, and PostgreSQL.

## Architecture
To meet the High Availability (HA) requirement, this application is fully containerized. Running the startup script initializes:
1. **PostgreSQL Database:** Acts as the single source of truth, utilizing pessimistic write-locking to prevent race conditions during concurrent trades.
2. **2x Spring Boot App Instances:** Two identical stateless nodes processing requests.
3. **Nginx Load Balancer:** Routes traffic between the two nodes. If one node goes down (e.g., via the `/exit` endpoint), Nginx instantly routes subsequent requests to the surviving node, ensuring **zero downtime**.

## Requirements
* Docker
* Docker Compose

## Quick Start
Clone the repository and run the startup script for your OS. Provide your desired port as a parameter.
Before running any commands, you must open your terminal or command prompt and navigate to the root folder of this project (the one containing the docker-compose.yml and start.bat/start.sh files).

**Linux / macOS:**
```bash
chmod +x start.sh
./start.sh 8080
```

**Windows (x64)**
   ```cmd
   start.bat 8080
```