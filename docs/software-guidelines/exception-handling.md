# Exception Handling Guidelines

## 1. Purpose

This document defines the custom exception hierarchy and error handling rules for `gam-api`.

The application avoids boilerplate resource-specific exception classes (e.g., `AccountNotFoundException`, `MemberNotFoundException`). Instead, the exception hierarchy is small, categorized strictly by error semantics (HTTP status), and relies on structured data to identify the affected domain resource.

## 2. Core Architecture Rules

### 2.1. Exception Type Represents the Category

Do not create a new exception class for every entity. The exception class strictly represents the *category* of the error, which maps directly to an HTTP response status.

* **Valid:** Throwing a shared `NotFoundException` or `ConflictException`.
* **Forbidden:** Creating `LocationNotFoundException`, `PresenceConflictException`, or `AccountAlreadyHasRoleException`.

### 2.2. Structured Data Over String Messages

Exceptions must carry structured data (e.g., `resource` and `identifier`) to allow the `GlobalExceptionHandler` to build machine-readable error responses. Do not rely exclusively on free-form string messages.

**Correct Usage:**

```java
throw NotFoundException.resource("Member", memberId);
```

## 3. The Exception Hierarchy

All business-level exceptions extend a shared base class, `ApplicationException`, which holds the structured `code`, `resource`, and `identifier`.

### 3.1. `NotFoundException` (404 Not Found)

**Usage:** Thrown when a requested resource does not exist.

```java
// Replaces MemberNotFoundException, EventNotFoundException, etc.
throw NotFoundException.resource("Event", eventId);
```

### 3.2. `ConflictException` (409 Conflict)

**Usage:** Thrown when a request cannot be completed because it conflicts with the current state of the system.

* *Examples:* Email is already registered, an account already has a specific role, or a presence is already registered for a member/event pair.

```java
throw ConflictException.resource(
        "Presence", 
        "%s:%s".formatted(memberId, eventId), 
        "Presence already registered for this member and event"
);
```

### 3.3. `ForbiddenOperationException` (403 Forbidden)

**Usage:** Thrown when the authenticated user is known, but the requested business operation is not allowed by domain rules.

* *Examples:* Trying to delete a system-managed role, removing a presence after an event is locked, or deleting a member with historical presences.

```java
throw ForbiddenOperationException.reason("System-managed roles cannot be edited, deleted, or disabled.");
```

### 3.4. `InvalidCommandException` (400 Bad Request)

**Usage:** Thrown when a command is structurally valid JSON (and passes basic `@Valid` checks) but violates complex application rules.

* *Examples:* Event end date is before the begin date, a partial email search value is too broad, or a required deletion reason is missing.

```java
throw InvalidCommandException.resource(
        "AccountRole",
        accountId,
        "Account role changes require an audit reason"
);
```

## 4. API Error Response Shape

The `GlobalExceptionHandler` catches the `ApplicationException` hierarchy and translates it into a stable `ApiErrorDTO` response.

Errors must include stable, machine-readable `details` populated from the structured exception data.

**Target JSON Shape:**

```json
{
  "timestamp": "2026-06-27T10:00:00Z",
  "status": 404,
  "code": "RESOURCE_NOT_FOUND",
  "message": "Member not found with identifier 3f7...",
  "details": {
    "resource": "Member",
    "identifier": "3f7..."
  }
}
```

## 5. Allowed Custom Exceptions

While resource-specific exceptions (e.g., `MemberNotFoundException`) are forbidden, highly specific custom exception classes are allowed **only if** they map to a distinct handling rule, metadata shape, or deep domain meaning that the shared hierarchy cannot accommodate.

**Valid Exceptions to Keep:**

* `InvalidPhoneNumberException`: Phone number parsing is a specialized value-object concern.
* `InvalidTokenFormatException` / `RefreshTokenExpiredException`: Token failures require specific authentication/session handling and have a completely different meaning than a missing business resource.
