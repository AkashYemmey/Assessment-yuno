# 🧪 Mini Payment Orchestration System - Test Case Documentation

---

## 📌 Overview

This document defines the test scenarios for the Payment Orchestration System.
It covers:

* Functional testing
* Negative scenarios
* Edge cases
* System behavior under failure conditions

---

## 🧪 Test Categories

| Category   | Description                    |
| ---------- | ------------------------------ |
| Sanity     | Basic functionality checks     |
| Functional | Core feature validation        |
| Negative   | Invalid input handling         |
| Edge       | Boundary and unusual scenarios |

---

# ✅ Positive Test Cases

---

### TC-01: Create Payment (CARD - Success)

* **Type:** Functional
* **Input:**

```json
{
  "amount": 100,
  "currency": "INR",
  "type": "CARD"
}
```

* **Expected Result:**

    * Routed to Provider A
    * Payment status = SUCCESS

---

### TC-02: Create Payment (UPI - Success)

* **Type:** Functional
* **Input:** type = UPI
* **Expected Result:**

    * Routed to Provider B
    * Payment status = SUCCESS

---

### TC-03: Fetch Payment by ID

* **Type:** Sanity
* **Input:** Valid payment ID
* **Expected Result:**

    * Payment details returned

---

### TC-04: Retry Logic Works

* **Type:** Functional
* **Scenario:** First attempt fails, second succeeds
* **Expected Result:**

    * Payment status = SUCCESS

---

### TC-05: Failover Works (A → B)

* **Type:** Functional
* **Scenario:** Provider A fails, Provider B succeeds
* **Expected Result:**

    * Payment status = SUCCESS
    * Provider = B

---

### TC-06: Idempotency Works

* **Type:** Functional
* **Scenario:** Same request with same key sent twice
* **Expected Result:**

    * Same response returned
    * No duplicate record in DB

---

# ❌ Negative Test Cases

---

### TC-07: Invalid Payment Type

* **Input:** type = "INVALID"
* **Expected Result:**

    * 400 Bad Request
    * Error message

---

### TC-08: Missing Idempotency Key

* **Expected Result:**

    * 400 Bad Request

---

### TC-09: Null Amount

* **Input:** amount = null
* **Expected Result:**

    * Validation error

---

### TC-10: Empty Currency

* **Input:** currency = ""
* **Expected Result:**

    * Validation error

---

### TC-11: Invalid Payment ID Format

* **Input:** id = "abc123"
* **Expected Result:**

    * 400 Bad Request
    * "Invalid ID" error

---

### TC-12: Payment Not Found

* **Input:** Valid ObjectId but not present
* **Expected Result:**

    * 404 Not Found

---

### TC-13: All Providers Fail

* **Scenario:** Provider A and B both fail
* **Expected Result:**

    * Payment status = FAILED

---

# ⚠️ Edge Test Cases

---

### TC-14: Duplicate Concurrent Requests

* **Scenario:** Same idempotency key sent simultaneously
* **Expected Result:**

    * Only one processed
    * Others return cached response

---

### TC-15: Large Amount Value

* **Input:** amount = very large value
* **Expected Result:**

    * System handles without failure

---

### TC-16: Slow Provider Response

* **Scenario:** Provider delay
* **Expected Result:**

    * Retry mechanism triggered

---

### TC-17: Invalid JSON Request

* **Input:** malformed JSON
* **Expected Result:**

    * 400 Bad Request

---

### TC-18: Missing Request Body

* **Expected Result:**

    * 400 Bad Request

---

# 📊 Summary

This test suite ensures:

* Correct payment routing
* Retry and failover behavior
* Idempotency handling
* Validation and error handling
* System stability under edge conditions

---
