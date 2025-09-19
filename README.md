# 📦 Parcel Tracking Number API

A Spring Boot (WebFlux) service that generates **unique parcel tracking numbers** using a **Snowflake ID generator**.  
The tracking number is globally unique, ordered by time, and supports concurrency-safe generation under high load.

---

## 🚀 Features
- Generate unique tracking numbers in **Base36** format.
- RFC 3339 timestamp support.
- Concurrency safe (tested with 1000+ parallel requests).
- Deployable via Docker or Render.

---

## ⚙️ Tech Stack
- Java 17
- Spring Boot (WebFlux)
- Maven
- Docker
- Render (deployment target)

---

## 🏗️ Build & Run Locally

### Prerequisites
- JDK 17+
- Maven 3.5+

### Clone the repo
```bash
git clone https://github.com/Divs2001/tracking-number.git
cd tracking-number/parcel-tracking
```

### Build
```bash
mvn clean package -DskipTests
```

### Run
```bash
java -jar target/parcel-tracking-0.0.1-SNAPSHOT.jar
```

The service will start at:  
👉 http://localhost:8080
Also, the hosted url is,
👉 https://tracking-number-xbtd.onrender.com/next-tracking-number?origin_country_id=MY&destination_country_id=ID
We can change the value of request params according to usecase.

---

## 📡 API Usage

### Endpoint
```
GET /next-tracking-number
```

### Query Parameters
| Name                 | Type      | Example Value                          | Description |
|-----------------------|-----------|----------------------------------------|-------------|
| origin_country_id     | String    | `MY`                                   | ISO 3166-1 alpha-2 |
| destination_country_id| String    | `ID`                                   | ISO 3166-1 alpha-2 |

For our use case, we could get unique IDs wih Snowflake method, so we don't require much of the query params, but yes, if there is some specific use case, we could add logic pertaining to that later.

### Example Request
```bash
curl -X GET "http://localhost:8080/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32+08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics" -H "Accept: application/json"
```

### Example Response
```json
{
  "tracking_number": "MYID2QEXSN32EO74",
  "created_at": "2025-09-19T21:59:01Z"
}
```

---

## 🧪 Concurrency Testing
You can run load tests to verify uniqueness:

```bash
mvn test -Dtest=TrackingConcurrencyTest -DtotalRequests=1000 -Dconcurrency=50
```

Output will show:
- total requests sent
- unique tracking numbers received

---

## 🐳 Docker Build

```bash
docker build -t parcel-tracking .
docker run -p 8080:8080 parcel-tracking
```

---

## 🌐 Deployment on Render
- Root Directory: `parcel-tracking`
- Build Command:
  ```bash
  ./mvnw clean package -DskipTests
  ```
- Start Command:
  ```bash
  java -jar target/parcel-tracking-0.0.1-SNAPSHOT.jar
  ```

---

## 👤 Author
**Divyansh Sharma**  
Software Developer | Backend Engineer  
