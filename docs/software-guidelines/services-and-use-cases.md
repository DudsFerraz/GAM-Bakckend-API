# Services and Use Cases Guidelines

## 1. Purpose

This document defines the naming conventions, structure, and responsibilities for application workflow classes, read operations, data loaders, and input/output data objects in `gam-api`.

The application layer favors small, focused use-case classes with expressive action or read names over monolithic service classes or vague naming conventions.

## 2. Naming Conventions

The application layer adheres to the following strict naming rules:

| Objective                                                               | Suffix         | Example              |
|-------------------------------------------------------------------------|----------------|----------------------|
| Represent an application workflow that performs an action               | *none*         | `RegisterMember`     |
| Represent a read operation that intentionally does not mutate state     | *none*         | `SearchMembers`      |
| Centralize repeated "load this required domain object or fail" behavior | `DomainLoader` | `MemberDomainLoader` |
| Centralize repeated "load this required JPA entity or fail" behavior    | `EntityLoader` | `MemberEntityLoader` |
| Represent input data received by an application operation               | `DTO`          | `RegisterMemberDTO`  |
| Represent output data returned by an application operation              | `RDTO`         | `RegisterMemberRDTO` |


## 3. Application Components

### 3.1. Action Workflows

**Core Concept:** Action workflows represent operations that mutate system state or perform specific business actions. They coordinate repositories, loaders, and rich domain models to enforce application-level rules. They are named using direct verb phrases without suffixes. Monolithic, generic services (e.g., `MemberService`) are not used.

**Example:** `RegisterMember` is named exactly for the action it performs (`RegisterMember`, not `RegisterMemberUseCase`). It exposes a simple execution method (like `execute()` or `register()`) and contains only the logic required to orchestrate the member registration process.

### 3.2. Read Operations

**Core Concept:** Read operations are dedicated to information retrieval and intentionally do not mutate state. They are named with read/search verb phrases without suffixes. To optimize performance, they prefer returning direct database projections, pages, or `RDTO`s, avoiding the reconstruction of rich domain models unless domain behavior is strictly required to answer the query.

**Example:** `SearchMembers` handles paginated queries and filtering (`SearchMembers`, not `SearchMembersQuery`). It queries the database using specifications and returns `MemberRDTO`s directly, keeping the read path completely independent of the write path logic.

### 3.3. Loaders

**Core Concept:** Loaders centralize the repeated "load this required object by ID or fail" behavior. To maintain the strict separation between domain models and persistence entities, loaders are explicitly typed. A single workflow should generally depend on only one loader type for a specific model; injecting both into the same workflow is a design smell.

**Examples:** `MemberDomainLoader` and `MemberEntityLoader`

* `MemberDomainLoader.requiredById(id)`: Returns the rich `Member` domain model. Used by action workflows that need to execute domain logic.
* `MemberEntityLoader.requiredById(id)`: Returns the JPA `MemberEntity`. Used by persistence-oriented operations or workflows interacting with simplified models.

### 3.4. Input and Output Data (DTO / RDTO)

**Core Concept:** `DTO` (Data Transfer Object) and `RDTO` (Response Data Transfer Object) are simple, data-oriented structures used for application input and output. They contain zero business rules and zero persistence logic. JPA entities are never exposed directly through controllers; they are always mapped to an `RDTO`.

**Examples:** `RegisterMemberDTO` and `RegisterMemberRDTO`

* `RegisterMemberDTO`: Mirrors the incoming HTTP request body. It may contain basic validation annotations but no domain behavior.
* `RegisterMemberRDTO`: Shapes the API response, exposing only the data the client is permitted to see.