# Rewards Assessment - Spring Boot REST API

This project calculates customer reward points for recorded purchase transactions.

## Requirement Implemented

A customer receives:

- 2 points for every dollar spent over $100 in each transaction
- 1 point for every dollar spent between $50 and $100 in each transaction

Example: `$120 = 2 * 20 + 1 * 50 = 90 points`

The API calculates reward points per customer, per month, and total for the selected date range. Months are not hard coded. The response is dynamically grouped from the transaction dates.

## Tech Stack

- Java 17
- Spring Boot 3.3.5
- Maven
- JUnit 5
- MockMvc for integration testing

## Project Structure

```text
src/main/java/com/retailer/rewards
├── controller   # REST endpoints
├── dto          # API request/response DTOs
├── exception    # Custom exceptions and global handler
├── model        # Transaction model
├── repository   # In-memory transaction repository
└── service      # Reward calculation business logic
```

## API Endpoints

### 1. Get rewards for all customers

```http
GET /api/rewards?startDate=2026-01-01&endDate=2026-03-31
```

### 2. Get rewards for a specific customer

```http
GET /api/rewards/C001?startDate=2026-01-01&endDate=2026-03-31
```

## Sample Response

```json
{
  "customerId": "C001",
  "customerName": "Aarav Sharma",
  "monthlyRewards": [
    { "year": 2026, "month": "JANUARY", "transactionCount": 2, "points": 115 },
    { "year": 2026, "month": "FEBRUARY", "transactionCount": 1, "points": 150 },
    { "year": 2026, "month": "MARCH", "transactionCount": 2, "points": 290 }
  ],
  "totalPoints": 555
}
```

## How to Run

```bash
mvn clean test
mvn spring-boot:run
```

Application starts on:

```text
http://localhost:8080
```

## Curl Commands

```bash
curl "http://localhost:8080/api/rewards?startDate=2026-01-01&endDate=2026-03-31"
```

```bash
curl "http://localhost:8080/api/rewards/C001?startDate=2026-01-01&endDate=2026-03-31"
```

## Negative Scenarios Covered

- Invalid date range where `startDate` is after `endDate`
- Blank customer id
- Customer not found
- Negative transaction amount in service-level test

## Notes

- The dataset is kept in-memory to demonstrate the solution clearly.
- `target/` and `bin/` folders are excluded through `.gitignore`.
- Unit and integration tests are included.
