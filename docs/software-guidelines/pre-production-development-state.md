# Pre-Production Development State

## Current lifecycle state

The system is in pre-production development. It has no production deployment, external users, or production data that must be preserved.

This lifecycle state is a project constraint, not an assumption to be reconsidered for each implementation. It remains in effect until the project explicitly declares production readiness or records a later lifecycle decision.

## Development-state policy

While the system is pre-production:

- Production migration planning is out of scope.
- Backward-compatibility layers for hypothetical users, data, clients, or stored formats must not be added.
- Legacy fallbacks, adapters, dual-read or dual-write paths, versioned compatibility formats, and deprecation shims must not be retained when they only protect unreleased behavior.
- Schema, API, authentication, authorization, persistence, and serialized-data changes may be made directly to the current implementation.
- Development fixtures, tests, documentation, and local database setup must be updated in the same change when a format or contract changes.
- Dead code that exists only to preserve unreleased behavior should be removed instead of maintained.

## Database migrations

Migration files may still be used to create and initialize local or test databases. However, production concerns are not required at this stage. Agents do not need to design zero-downtime sequencing, rollback compatibility, historical-data preservation, or multi-version deployment support for pre-production changes.

When a schema or fixture contract changes, update the development and test database setup to the new contract. Do not preserve an old schema or data format solely because it existed in an earlier unreleased revision.

## Exceptions

Compatibility work is in scope only when it is explicitly required by a current Requirement Specification, ADR, external integration contract, or an approved project decision. If that happens, document the exception and its intended removal or review point.

This guideline must be revisited when production readiness, the first real user, or the first production dataset is introduced.
