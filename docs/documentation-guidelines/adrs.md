# ADR documentation

## Purpose

ADRs document architecture and design decisions. Use ADRs when a decision has meaningful consequences, tradeoffs, or future maintenance impact. Examples of decisions that deserve ADRs:

* Using value objects for domain validation
* Using Java records for immutable domain objects
* Choosing layered architecture
* Choosing a persistence strategy
* Choosing validation boundaries
* Choosing Mermaid for diagrams

Do not create ADRs for trivial implementation details.

---

## ADR file naming

```text
docs/decisions/0001-use-value-objects.md
docs/decisions/0002-use-mermaid.md
docs/decisions/0003-video-documentation-policy.md
```

* Use sequential numbers.
* Do not rename old ADRs casually.
* Do not delete old ADRs just because the decision changed.
* If a decision changes, create a new ADR that supersedes the previous one.

---

## ADR statuses

* `Proposed`: under discussion.
* `Accepted`: current decision.
* `Deprecated`: no longer recommended.
* `Superseded`: replaced by a newer ADR.
* `Rejected`: considered but intentionally not chosen.

---

## ADR template

```md
# ADR-0000: <Decision title>

## Status
Proposed | Accepted | Deprecated | Superseded | Rejected

## Context
What problem are we solving?

What constraints, requirements, or forces influenced this decision?

## Decision
What did we decide?

## Alternatives considered
### Option 1: <Name>
Pros:
- ...

Cons:
- ...

### Option 2: <Name>
Pros:
- ...

Cons:
- ...

## Consequences
Positive consequences:
- ...

Negative consequences:
- ...

## Related requirements
- REQ-AREA-001

## Related diagrams
- `docs/diagrams/<diagram>.md`

## Related videos
- Video link
```

---

## ADR example

The following example demonstrates the expected level of detail and tradeoff analysis. It is not a second template; the ADR template above remains the canonical structure.

```text
docs/decisions/0001-use-value-objects.md
```

```md
# ADR-0001: Use value objects for domain validation

## Status
Accepted

## Context
The system contains domain concepts that have validation rules and normalization behavior.

For example, a personal name is not just a pair of strings. It has rules for required fields, length limits, allowed characters, and normalization.

If these rules are spread across controllers, services, DTOs, and persistence code, the system becomes harder to maintain and easier to misuse.

## Decision
Use value objects to represent domain concepts with their own validation and normalization rules.

The `Name` concept shall be represented as a value object.

## Alternatives considered

### Option 1: Validate only in controllers
Pros:
- Simple to implement initially.
- Keeps domain objects minimal.

Cons:
- Validation can be bypassed by non-controller code.
- Business rules become duplicated across endpoints.
- Tests may accidentally validate controller behavior instead of domain behavior.

### Option 2: Validate only with database constraints
Pros:
- Guarantees some persistence-level constraints.
- Useful as a final safety layer.

Cons:
- Database constraints do not clearly express domain intent.
- Error messages are usually less useful.
- Business rules are discovered too late.
- Some rules are difficult or inappropriate to enforce only in the database.

### Option 3: Use value objects
Pros:
- Centralizes validation.
- Makes invalid domain states harder to create.
- Makes domain rules easier to test.
- Makes requirements easier to map to code.

Cons:
- Requires more explicit modeling.
- Requires care when integrating with JPA.
- May require extra mapping between DTOs, entities, and domain objects.

## Consequences

Positive consequences:
- Domain validation is centralized.
- LLM agents and developers have a clear place to implement rules.
- Tests can focus directly on domain behavior.
- Invalid names cannot be created accidentally.

Negative consequences:
- More domain classes may be created.
- Persistence mapping may require additional configuration.
- Developers must understand the difference between DTOs, entities, and value objects.

## Related requirements
- `docs/requirements/common/name.md`
- `REQ-NAME-001`
- `REQ-NAME-002`
- `REQ-NAME-003`
- `REQ-NAME-004`

## Related diagrams
- `docs/diagrams/name-validation-flow.md`

## Related videos
- Video link
```

---
