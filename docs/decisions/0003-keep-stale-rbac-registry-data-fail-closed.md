# ADR-0003: Keep stale RBAC registry data fail-closed

## Status
Accepted

## Context

The RBAC catalog is synchronized from a code-defined registry, while persisted Roles, Permissions, role-permission links, Account-role assignments, and Event permission references use stable UUIDs. Registry entries and baseline system-role bundles can change during development.

Deleting a removed registry row automatically can destroy history or invalidate persisted references. Leaving removed data active, however, can preserve authority that the accepted registry no longer grants. A future sensitive permission must also not reach `COORD` merely because it was added to the registry.

The project is pre-production, so current schema setup, fixtures, and source references can be updated directly. Persisted identities may still exist in local databases or be required for security history, and synchronization must behave deterministically when an entry disappears, reappears, or collides with custom data.

## Decision

The Accepted RBAC Catalog Requirement Specification defines the current system registry and baseline bundles. The code-defined registry implements that contract and drives repeatable synchronization.

Registry removal shall be non-destructive but fail-closed:

- a system-managed Role or Permission absent from the accepted registry becomes stale;
- a role-permission link absent from the accepted bundle of a system Role becomes stale;
- stale registry records and links remain persisted for identity, references, history, explicit cleanup, or later reappearance;
- stale data contributes no runtime permission authority and is excluded from ordinary RBAC catalog reads and new authorization configuration; and
- newly initialized databases, fixtures, and source references contain only the current accepted registry.

Synchronization shall match Roles by stable name and Permissions by stable code across active and soft-deleted rows. A unique system-managed match keeps its UUID, is restored if soft-deleted, and receives the accepted metadata. A unique preserved registry-owned role-permission link is likewise reused when its bundle entry reappears.

Synchronization shall fail without committing partial catalog changes when:

- a custom active or soft-deleted record uses a system registry key;
- more than one persisted record matches a registry key; or
- more than one persisted link matches a system bundle pair.

Synchronization shall never convert a custom record into a system-managed record to resolve a collision.

`SUDO` remains the only baseline role that automatically receives every current system permission. `COORD` and other baseline roles receive explicit allowlists from the Accepted RBAC Catalog Requirement Specification.

## Alternatives considered

### Option 1: Automatically delete or soft-delete removed registry data

Pros:
- Ordinary persistence queries naturally stop seeing removed records.
- Runtime authorization does not need to distinguish current and stale registry data.

Cons:
- Automatic deletion can invalidate Event references and erase security identity or history.
- A repeatable seed would perform destructive lifecycle changes during application startup.
- Reappearance could create a new identity or require ambiguous restoration behavior.

### Option 2: Preserve removed data as authoritative until cleanup

Pros:
- Requires little change to authorization loading.
- Existing assignments and links continue to work until a Developer cleans them up.

Cons:
- Removed permissions can continue authorizing sensitive operations.
- Removing a permission from an accepted bundle would not reliably revoke it.
- Runtime behavior would contradict the accepted registry.

### Option 3: Preserve stale data but exclude it from authority and ordinary reads

Pros:
- Fails closed when the registry contracts.
- Preserves UUIDs, references, and security history.
- Supports deterministic reappearance without silently converting custom data.
- Prevents future permissions from expanding `COORD` unless deliberately allowlisted.

Cons:
- Authorization and catalog reads must evaluate current registry membership and current system bundles.
- Stale data remains in persistence until an explicit cleanup workflow handles it.
- Registry changes require concurrency and collision verification beyond simple idempotent inserts.

## Consequences

Positive consequences:
- The Accepted RBAC Catalog remains authoritative even when older database rows survive.
- Removed permissions and system-role links cannot silently retain access.
- Reintroduced registry entries preserve their original UUID when one unambiguous system-managed match exists.
- Custom/system collisions fail visibly instead of corrupting record ownership.

Negative consequences:
- Security-context loading and ordinary catalog reads must filter stale registry data.
- System-role permission loading must honor the accepted bundle, not every active persisted link.
- Explicit developer maintenance is still needed for permanent cleanup.
- Implementation tests must cover stale records, stale links, reappearance, soft-deleted matches, and collisions.

## Related requirements

- `REQ-RBAC-003`
- `REQ-RBAC-004`
- `REQ-RBAC-005`
- `docs/requirements/rbac/rbac-catalog.md`

## Related diagrams

- The registry synchronization flow in `docs/requirements/rbac/rbac-catalog.md`.

## Related videos

- None.
