# Swagger/OpenAPI documentation

## Purpose

Swagger/OpenAPI documents the backend API contract.

Use OpenAPI to document:

- Endpoints
- Request bodies
- Response bodies
- HTTP status codes
- Validation constraints visible to API consumers
- Authentication requirements
- Error response formats
- Examples

OpenAPI does not replace requirements. Requirements explain the business rule. OpenAPI exposes the API contract.

Example relationship:

```text
docs/requirements/common/name.md
    defines REQ-NAME-002 and REQ-NAME-003

Name.java
    implements the rule

NameFunctionalTest.java
    verifies the rule

OpenAPI schema
    documents the rule for API consumers
````

---

## OpenAPI documentation rule

Whenever an API request or response exposes a field governed by a requirement, the OpenAPI schema should reflect the externally visible rule. Example:

```yaml
firstName:
  type: string
  minLength: 2
  maxLength: 32
  example: Eduardo
  description: >
    Person's given name. Must follow REQ-NAME-002 and REQ-NAME-003.
```

---
