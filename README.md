# SMS Phishing Detection Microservice

## 📝 Description
This microservice is designed to protect mobile users from phishing attacks delivered via SMS. The system automatically analyzes message content, extracts suspicious URLs, and verifies them against security databases to prevent fraud.

### Key Features:
* **Phishing Detection:** Automated URL extraction and verification using external security APIs.
* **Subscription Management:** Users can toggle protection on/off by sending `START` or `STOP` commands to a dedicated endpoint.
* **High Performance:** Implementation of a custom two-level caching mechanism to ensure minimal latency.
* **Data Persistence:** Reliable storage for user subscription statuses and verified URL history using a relational database (PostgreSQL).

---

## Approach

The project follows **Clean Architecture** and **SOLID** principles to ensure maintainability and scalability.

 Multi-level Caching
To minimize costs of external API calls and maximize response speed, I implemented:
* **L1 (In-Memory):** **Caffeine Cache** – provides sub-millisecond access for frequently checked URLs.
* **L2 (Database):** **PostgreSQL** (`VerifiedUrl` table) – ensures verification results persist even after application restarts.

---

## 🚀 System Workflow (Step-by-Step)



1.  **SMS Reception:** The system receives a `POST` request at the `/process` endpoint.
2.  **Subscription Check:** The service queries PostgreSQL to see if the recipient has an active protection subscription.
3.  **URL Extraction:** If protected, a Regex-based service extracts URLs from the message body.
4.  **Phishing Verification:**
    * **Step A:** Check **Caffeine Cache** (RAM).
    * **Step B:** If not found, check the **VerifiedUrl** table (Database).
    * **Step C:** If still not found, call the **External API**, then save the result to both the Database and Cache.
5.  **Final Action:** If phishing is detected, the system returns `403 Forbidden`. Otherwise, it returns `200 OK`.

---

## 🛠️ Installation & Setup

### Prerequisites:
* **Docker & Docker Compose**
* **Java 21**
* **Maven**

### Running the application:

1.  **Build the project:**
    ```bash
    mvn clean package -DskipTests
    ```

2.  **Spin up the environment:**
    ```bash
    docker-compose up --build
    ```

---

## 🧪 API Testing (Quick Start)

You can test the service using **Postman** or the following **cURL** commands:

### 1. Activate Protection (Subscription)
Run this to enable phishing protection for a specific phone number:
```bash
curl --location 'http://localhost:8080/api/v1/sms/subscription' \
--header 'Content-Type: application/json' \
--data '{
    "sender": "48700800999",
    "message": "START"
}'