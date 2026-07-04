# Search Specifications Guidelines

## 1. Purpose

This document defines the rules for search specifications in `gam-api`.

Search Specifications provide flexible querying without requiring endless repository methods. However, the public API must expose a **strict, explicit product contract**. Internal JPA paths, database joins, parser details, and custom predicate behavior must remain hidden behind resource-specific filter definitions.

---

## 2. Core Architecture Rules

### 2.1. Separation of Public API and Internal JPA Paths

Search APIs never expose internal database structures or nested JPA paths. Every searchable resource defines explicit public aliases that map to internal targets or custom predicates.

**Example Mapping:**
Instead of forcing the client to know the database structure, the API exposes clean public fields.

| Public Field | Internal JPA Target              | Allowed Methods  |
|--------------|----------------------------------|------------------|
| `locationId` | `location.id`                    | `EQUALS`         |
| `role`       | `account.accountRoles.role.name` | `EQUALS`, `IN`   |
| `email`      | `account.email`                  | `EQUALS`, `LIKE` |

### 2.2. Explicit Filter Definitions

Each searchable resource (e.g., `Member`, `Account`, `Event`) explicitly defines its allowed filters. A valid filter definition strictly dictates:

1. The **public field name**.
2. The **internal JPA path** or **custom predicate**.
3. The **allowed comparison methods** (e.g., `EQUALS`, `LIKE`, `IN`, `GREATER_THAN_OR_EQUAL`).
4. The **parser/converter rules** required to map the input to the persistence type.

### 2.3. Specification Construction Flow

The generic `SpecificationBuilder` never accepts arbitrary public field strings. The request payload goes through a strict validation and conversion boundary before hitting the database:

```text
SearchDTO (Public API) 
  ➔ ResourceFilterConverter (Validates fields, methods, and parses values)
    ➔ Internal SpecificationFilter or Custom Specification
      ➔ SpecificationBuilder (Generates JPA Criteria)
```

---

## 3. Parsing and Predicate Guidelines

### 3.1. DTO Value Shape and Standard Parsing

To support both scalar values (for `EQUALS`) and collections (for `IN`), the `value` field in the request payload uses a flexible type like `JsonNode`. The filter definition is responsible for parsing this raw JSON into the exact type expected by the JPA entity.

* `UUID` fields ➔ Parsed as `UUID`
* Audit/Event datetimes ➔ Parsed as `Instant`
* Enums ➔ Parsed as the specific enum type

### 3.2. Contextual Parsing and Custom Predicates

When a simple field-path mapping is insufficient, or when different comparison methods require different data shapes, use custom predicates and contextual parsing.

**Example 1: Cross-Field Search (`Member name`)**
The public `name` field does not map to a single database column. Using the `LIKE` method triggers a custom predicate that searches both `name.firstName` and `name.surname` simultaneously, completely hiding the internal `Name` value object structure from the client.

**Example 2: Contextual Parsing (`email` and `phoneNumber`)**
The parser applies different rules based on the comparison method:

* **`email EQUALS`**: Parses strictly to the rich `MyEmail` domain object.
* **`email LIKE`**: Parses as a normalized `String`. It applies custom validation (rejects blank values, trims/lowercases, rejects inputs shorter than 3 characters, and enforces structural rules like requiring at least 2 characters before an `@`).

---

## 4. Validation and Error Handling

Invalid filters must **fail fast**. The API immediately rejects the request if:

* The client sends an unknown public field.
* The requested comparison method is not explicitly allowed for that field.
* The value cannot be parsed into the target persistence type.
* Partial searches (like `LIKE` on emails) fail their specific structural rules.

**Error Message Rule:** Validation errors must always reference the **public field name**. Never expose the internal JPA path in an error message.

* **Correct:** `Invalid filter value for email.`
* **Forbidden:** `Invalid filter value for account.email.`