# ADR-0009: Enforce Active GamLocation Duplicate Identity in Persistence

## Status

Accepted

## Context

`REQ-GAM-LOCATION-007` defines active GamLocation uniqueness over six fields with case-insensitive, accent-insensitive, and repeated-whitespace-insensitive comparison while preserving punctuation. Optional address fields normalize absence consistently, and soft-deleted rows do not reserve identities.

An application-only lookup before create or update cannot prevent two concurrent transactions from committing equivalent active records. Relying directly on database collation or ad hoc expression indexes would make the exact Unicode, accent, whitespace, and null behavior dependent on database configuration and function-index restrictions.

GAM is pre-production, so the persistence schema can move directly to the accepted representation without a compatibility layer.

## Decision

Persist internal canonical comparison keys for `name`, `street`, `city`, `state`, `postalCode`, and `countryCode` alongside each GamLocation record.

One shared application component shall derive those keys from the already validated field values by:

- applying locale-independent case normalization;
- removing accent distinctions;
- collapsing each run of internal whitespace to one space;
- preserving punctuation distinctions; and
- mapping an absent optional field to one consistent non-null canonical key.

The public API shall continue to expose the user's trimmed display values, not the canonical comparison keys.

The database shall enforce one partial unique constraint across all six canonical keys for active rows only. Create and update workflows shall perform a friendly duplicate lookup so they can return `GAM_LOCATION_ALREADY_EXISTS` with the existing UUID. The database constraint shall remain the final concurrency guard, and unique-constraint races shall be translated to the same domain conflict.

Update shall exclude its own UUID from the friendly lookup. Soft-deleted rows shall be outside the partial unique constraint.

## Alternatives considered

### Option 1: Application lookup only

Pros:
- Minimal schema changes.
- Easy to return the existing UUID in ordinary conflicts.

Cons:
- A check-then-insert or check-then-update race can create duplicates.
- Correctness depends on a single application instance and timing.

### Option 2: Database collation or expression-only normalization

Pros:
- Avoids persisted comparison keys.
- Keeps uniqueness entirely in a database expression.

Cons:
- Accent and Unicode behavior can vary with database collation and extension configuration.
- Some normalization functions are unsuitable for immutable index expressions.
- Null and repeated-whitespace semantics become difficult to inspect and keep aligned with API validation.

### Option 3: Persist canonical comparison keys with a partial unique constraint

Pros:
- Makes comparison semantics explicit and testable.
- Enforces atomic active uniqueness across API instances.
- Keeps soft-deleted identities reusable.
- Avoids hash-collision risk because canonical text keys, not a digest alone, form the constraint.

Cons:
- Adds internal persistence columns.
- Requires every write path to use the shared canonicalization component.
- Canonicalization-rule changes require deliberate schema/data regeneration.

## Consequences

Positive consequences:
- Concurrent create and update requests cannot commit equivalent active records.
- Friendly prechecks and database races produce the same public conflict contract.
- Display spelling remains independent of duplicate comparison.

Negative consequences:
- Persistence tests must verify canonical key derivation and the active-only constraint.
- Developer restoration may fail when an active record already reserves the same duplicate identity.
- Future comparison-rule changes must update requirements, canonicalization, and persisted keys together.

## Related requirements

- `REQ-GAM-LOCATION-002`
- `REQ-GAM-LOCATION-003`
- `REQ-GAM-LOCATION-007`
- `REQ-GAM-LOCATION-009`

## Related diagrams

- Inline flow in `docs/requirements/gam-locations/gam-location-records.md`

## Related videos

- None.
