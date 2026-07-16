# ADR-0010: Serialize GamLocation Mutation and Event Linking

## Status

Accepted

## Context

`REQ-GAM-LOCATION-010` forbids removing a GamLocation referenced by any Event, including historical or soft-deleted Events. `REQ-GAM-LOCATION-013` also forbids Event creation/linking and GamLocation removal from racing into a committed Event reference to a removed record. Update racing removal must not resurrect or mutate a deleted record.

A foreign key alone protects the physical row from hard deletion but does not prevent soft deletion. Independent application prechecks can both observe an apparently valid state and then commit incompatible mutations. Process-local synchronization would fail when more than one API instance is running.

## Decision

GamLocation update, GamLocation removal, and Event creation or linking shall acquire a database row-level lock on the same active GamLocation row inside their business transaction before mutating or establishing the reference.

Removal shall:

1. lock and revalidate the active GamLocation;
2. count every Event reference using a persistence path that includes historical and soft-deleted Event rows;
3. return `GAM_LOCATION_IN_USE` without mutation when any reference exists; and
4. otherwise soft-delete and audit the GamLocation in the same transaction.

Event creation or linking shall lock and revalidate the active GamLocation before persisting the reference. GamLocation update shall use the same lock boundary so removal cannot be overwritten or reversed by a stale update.

The lock shall be database-backed and transaction-scoped. If a future workflow needs to lock multiple GamLocation rows, it shall acquire them in deterministic UUID order to avoid introducing cyclic lock ordering.

## Alternatives considered

### Option 1: Foreign key plus ordinary reads

Pros:
- No explicit locking code.
- The database still protects against hard deletion.

Cons:
- Does not protect soft-delete visibility.
- Event linking and removal can both pass prechecks and commit.

### Option 2: Process-local mutex

Pros:
- Straightforward in one application process.
- Avoids explicit database lock queries.

Cons:
- Fails across multiple API instances.
- Adds memory lifecycle and key-cleanup concerns.

### Option 3: Database row-level serialization

Pros:
- Coordinates all API instances through the authoritative persistence boundary.
- Serializes only operations targeting the same GamLocation.
- Preserves transactional audit and lifecycle consistency.

Cons:
- Conflicting operations may wait for the current transaction.
- Lock ordering must be maintained if multi-location workflows are introduced.
- Integration tests must exercise the concurrency boundary with real transactions.

## Consequences

Positive consequences:
- A committed Event never acquires a concurrently removed GamLocation.
- Update cannot resurrect or overwrite a concurrently removed record.
- Removal reference checks include historical data hidden from normal repositories.

Negative consequences:
- Event creation and GamLocation mutations for the same place have lower concurrency.
- Persistence code needs an explicit active-row locking path and a historical-reference count path.

## Related requirements

- `REQ-GAM-LOCATION-009`
- `REQ-GAM-LOCATION-010`
- `REQ-GAM-LOCATION-013`

## Related diagrams

- Inline flow in `docs/requirements/gam-locations/gam-location-records.md`

## Related videos

- None.
