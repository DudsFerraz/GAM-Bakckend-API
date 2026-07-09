# Requirement: GamEmail

## Status
Accepted

## Context
GAM uses email addresses for accounts and may use them in other features. Email handling must be deterministic so that login, uniqueness checks, persistence, and API output agree on the same representation.

The `GamEmail` primitive defines the valid shape and canonical representation of a present email address. Whether an email address is required, unique, verified, or associated with a specific workflow belongs to the owning feature's Requirement Specification.

## Ubiquitous Language
- `normalized email`: The email value after trimming surrounding whitespace and lowercasing.

## Functional requirements

### REQ-GAM-EMAIL-001: Required present value
The `GamEmail` primitive shall reject null and blank values when a `GamEmail` value is created.

Rationale:
A present email primitive must represent an actual email address value, not absence.

Valid examples:
- `user@example.com`
- `User.Name+tag@Example.COM`

Invalid examples:
- `null`
- `""`
- `"   "`

---

### REQ-GAM-EMAIL-002: Normalization
The `GamEmail` primitive shall trim surrounding whitespace and lowercase the whole email address before validation, storage, and exposure.

Rationale:
Normalized email values make uniqueness checks and login behavior predictable.

Valid examples:
- `" User.Name+tag@Example.COM "` becomes `user.name+tag@example.com`
- `"USER@EXAMPLE.COM"` becomes `user@example.com`

Invalid examples:
- Storing `" User.Name+tag@Example.COM "` as entered.
- Storing `USER@EXAMPLE.COM` with uppercase letters.

---

### REQ-GAM-EMAIL-003: Practical syntax validation
The `GamEmail` primitive shall accept normalized values with practical email syntax.

The `GamEmail` primitive shall reject normalized values that are not syntactically usable email addresses.

Rationale:
The primitive should prevent obvious invalid values without depending on external provider behavior.

Valid examples:
- `user@example.com`
- `user.name+tag@example.com`

Invalid examples:
- `not-an-email`
- `user@`
- `@example.com`

---

### REQ-GAM-EMAIL-004: Deterministic validation boundary
The `GamEmail` primitive shall not prove deliverability, check mailbox existence, check DNS or MX records, reject disposable providers, or apply provider-specific equivalence rules.

Rationale:
Email validation must remain deterministic and must not depend on network availability or external provider policy.

Valid examples:
- `first.last@example.com` remains distinct from `firstlast@example.com`.
- `user+tag@example.com` remains distinct from `user@example.com`.

Invalid examples:
- Rejecting an email only because its domain cannot be contacted during validation.
- Treating Gmail dot or plus aliases as the same primitive value.

## Acceptance scenarios

```gherkin
Scenario: Normalize email before storing it
  Given the raw email value is " User.Name+tag@Example.COM "
  When a GamEmail value is created
  Then the creation succeeds
  And the stored value is "user.name+tag@example.com"

Scenario: Reject invalid email syntax
  Given the raw email value is "user@"
  When a GamEmail value is created
  Then the creation fails

Scenario: Preserve provider-specific aliases
  Given the first raw email value is "user+tag@example.com"
  And the second raw email value is "user@example.com"
  When both GamEmail values are created
  Then both values remain distinct
```

## Open questions

* None.

## Out of scope

* Account uniqueness policies.
* Email verification workflows.
* Deliverability checks.
* Disposable-provider blocking.
* Provider-specific alias normalization.

## Related ADRs

* None.

## Related videos

* None.
