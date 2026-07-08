# Mermaid diagrams

## Purpose

Mermaid is used for diagrams stored as text inside Markdown files. Use Mermaid for:

* Validation flows
* Business workflows
* Sequence diagrams
* State diagrams
* Backend architecture overviews
* Request/response flows
* Decision flows

Mermaid diagrams must support the written documentation, not replace it.

---

## Mermaid file location

Small diagrams may live directly inside requirement or ADR files.

Larger diagrams should live in:

```text
docs/diagrams/
```

Example:

```text
docs/diagrams/name-validation-flow.md
docs/diagrams/backend-overview.md
```

---

## Mermaid flowchart example

````md
# Name Validation Flow

```mermaid
flowchart TD
    A[Receive firstName and surname] --> B{Any field null?}
    B -- Yes --> R[Reject]
    B -- No --> C[Trim input]
    C --> D{Length valid?}
    D -- No --> R
    D -- Yes --> E{Characters valid?}
    E -- No --> R
    E -- Yes --> F[Accept]
````

Rendered meaning:

```text
Receive input
  -> reject if null
  -> trim
  -> reject if length is invalid
  -> reject if characters are invalid
  -> accept
````

---

## Mermaid sequence diagram example

````md
# Create User Request Flow

```mermaid
sequenceDiagram
    participant Client
    participant Controller as UserController
    participant Service as UserService
    participant Name as Name Value Object
    participant Repository as UserRepository
    participant DB as Database

    Client->>Controller: POST /users
    Controller->>Service: createUser(request)
    Service->>Name: new Name(firstName, surname)

    alt Name is invalid
        Name-->>Service: validation error
        Service-->>Controller: error
        Controller-->>Client: 400 Bad Request
    else Name is valid
        Service->>Repository: save(user)
        Repository->>DB: insert user
        DB-->>Repository: success
        Repository-->>Service: saved user
        Service-->>Controller: response
        Controller-->>Client: 201 Created
    end
```
````

---

## Mermaid architecture overview example

```md
# Backend Overview

```mermaid
flowchart LR
    Client[API Client] --> API[Spring Boot API]
    API --> DB[(PostgreSQL Database)]

    subgraph Spring Boot API
        Controller[Controllers]
        Service[Services]
        Domain[Domain Objects]
        Repository[Repositories]

        Controller --> Service
        Service --> Domain
        Service --> Repository
    end

    Repository --> DB
````

---
