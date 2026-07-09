# Requirement: UUID Identity

## Status
Accepted

## Context
Persisted GAM resources need identifiers that can be used consistently across domain models, persistence, DTOs, API paths, and relationships. The project uses UUID identity so identifiers are globally unique and do not expose database sequence information.

This Requirement Specification defines the common identity convention. Resource-specific lifecycle rules, authorization rules, lookup behavior, and not-found behavior belong to the owning feature's Requirement Specification.

## Ubiquitous Language
- `resource identifier`: The UUID value that identifies a persisted resource.
- `UUID v7`: A time-ordered UUID version suitable as the default for newly created persisted resources.
- `UUID v4`: A random UUID version available only when a use case explicitly needs random UUIDs.

## Functional requirements

### REQ-GAM-ID-001: UUID resource identity
Persisted GAM resources shall use UUID values as their public and internal resource identifiers.

Rationale:
UUID identifiers are globally unique and avoid exposing database sequence information in APIs and relationships.

Valid examples:
- An Account is identified by a UUID.
- A Member is identified by a UUID.
- An Event is identified by a UUID.
- A Location is identified by a UUID.

Invalid examples:
- A new persisted resource uses an auto-incrementing integer as its public identifier.
- A DTO exposes a database sequence value as the resource identifier.

---

### REQ-GAM-ID-002: UUID identity across layers
Domain models, persistence models, DTOs, API paths, and relationship fields shall represent persisted resource identifiers as UUID values.

Rationale:
Using the same identifier type across layers avoids unnecessary conversion rules and prevents accidental exposure of alternate identifiers.

Valid examples:
- An API path accepts a UUID when retrieving a resource by identifier.
- A response DTO exposes the resource identifier as a UUID.
- A relationship field stores the related resource identifier as a UUID.

Invalid examples:
- A controller accepts a string identifier that is not parsed as a UUID.
- A response DTO exposes a separate numeric ID for the same persisted resource.

---

### REQ-GAM-ID-003: UUID v7 default for new resources
Newly created persisted GAM resources shall receive UUID v7 identifiers by default.

Rationale:
UUID v7 provides globally unique identifiers with time ordering that is friendlier to persistence and operational inspection than fully random identifiers.

Valid examples:
- Registering a new Account creates a UUID v7 identifier.
- Registering a new Member creates a UUID v7 identifier.
- Creating a new Event creates a UUID v7 identifier.

Invalid examples:
- A new resource factory creates a UUID v4 identifier without a documented reason.
- A new resource uses a database-generated integer identifier by default.

---

### REQ-GAM-ID-004: UUID v4 exception boundary
UUID v4 may be used only when a requirement or decision explicitly calls for random UUID identifiers.

Rationale:
Keeping UUID v4 as an exception prevents the project from mixing identity strategies accidentally.

Valid examples:
- A future security token requirement explicitly chooses UUID v4 because time ordering would be undesirable.

Invalid examples:
- A new persisted domain resource uses UUID v4 because it is convenient.
- A migration seeds resource identifiers with UUID v4 without a documented reason.

## Acceptance scenarios

```gherkin
Scenario: New resource receives UUID v7 identity
  Given a persisted GAM resource is created
  When its identifier is assigned
  Then the identifier is a UUID
  And the UUID version is 7

Scenario: Resource identifier is exposed consistently
  Given a persisted resource exists
  When the resource is loaded through the API
  Then its response identifier is the same UUID identity used by the domain and persistence layers

Scenario: Reject accidental numeric identity for a new resource
  Given a new persisted resource is being designed
  When its public identifier is specified
  Then the identifier must not be an auto-incrementing integer
```

## Open questions

* None.

## Out of scope

* Resource-specific authorization or visibility rules.
* Not-found behavior for lookup use cases.
* Soft-delete behavior.
* External integration identifiers.
* Human-readable codes or slugs.

## Related ADRs

* None.

## Related videos

* None.
