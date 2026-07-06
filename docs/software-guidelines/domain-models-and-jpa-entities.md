# Domain Models and JPA Entities Guidelines

## 1. Purpose

This document defines the guidelines for domain model and JPA entity separation in `gam-api`.

The domain/entity split is not a mandatory pattern for every persisted model. Each separated domain model must justify its existence through business behavior, invariants, lifecycle rules, or important domain language. A separated domain model does not exist merely to duplicate the fields of a JPA entity before saving or reading a row.

## 2. Core Architecture Rules

### 2.1. Rich Domain Models

Rich domain models carry meaningful business logic and are separated from their persistence entities.

* The domain class is entirely separated from the JPA entity.
* Meaningful business rules, state transitions, and invariants live inside the domain class or a closely related domain policy.
* Persistence annotations (JPA) are strictly forbidden in the domain class.
* JPA relationships, lazy-loading concerns, table mappings, and persistence-only details reside exclusively inside the entity.
* Mappers are used to cross the boundary between the rich domain model and the persistence entity.
* Rich domain classes are not passive data containers; they protect their state and expose intentional business methods.

### 2.2. Simplified Models

Simplified models do not require a separated domain class.

* The JPA entity is used directly in persistence-oriented workflows.
* There is no separated domain class that only duplicates entity fields.
* JPA entities remain simple data structures. Simplified entities are constructed and populated directly in the application layer without rich factory methods inside the entity itself.
* Business rules and validations for these models reside in an application service or policy.
* There is no domain-to-entity mapping overhead (`Entity -> Domain -> Entity`) for models that lack domain behavior.

## 3. Model Design Guidelines

Whether a concept requires a rich domain model or a simplified entity depends on its role in the application's business logic.

### 3.1. When to Use a Rich Domain Model

**Core Concept:** Use a separated domain model when the concept encapsulates complex business rules, strict invariants, or specific lifecycle phases. If the object must protect its internal state from invalid mutations and expose intentional business actions rather than simple getters and setters, it requires a rich domain class.

**Example:** `Member` is designed as a rich domain model because it manages a complex lifecycle. It handles registration, age calculation, and status transitions. Instead of exposing a `setStatus()` method, it exposes intentional operations like `activate()` and `deactivate()`. Activation is not just a status update; it enforces domain invariants and coordinates side-effects, such as triggering an account role transition. This behavior justifies the separation from `MemberEntity`.

### 3.2. When to Use a Simplified Model

**Core Concept:** Use the JPA entity directly when the concept acts primarily as a data container, a configuration record, or a database join table. If the business logic is simple CRUD, or if rules can safely reside in an application service or database constraint without compromising domain integrity, the overhead of a separated domain class is unnecessary.

**Example:** `Location` is treated as a simplified model. It is purely a structured data record without internal state transitions or complex lifecycle rules. Field validation and data trimming happen at the request or application layer. Because there is no internal domain behavior to protect, `LocationEntity` is used directly in persistence workflows, avoiding the unnecessary creation of a separated domain object and its associated mappers.

## 4. Mapper Usage

Mappers are strictly aligned with the domain/entity separation rules:

* **Rich Boundaries Only:** Mappers are used exclusively to cross the boundary between rich domain models and their respective persistence entities.
* **No Mapping for Simplified Models:** Mappers are never created or used for simplified models. These models are constructed and interacted with directly as entities in the application layer, avoiding empty `Entity -> Domain -> Entity` mapping cycles.
