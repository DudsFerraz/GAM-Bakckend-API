# ADR-0008: Generate and Govern OpenAPI from Backend Code

## Status
Accepted

## Context
GAM's frontend and backend live in separate repositories. Frontend Developers need an exact, discoverable API contract and generated TypeScript types without copying backend DTOs or learning backend implementation details.

The accepted web-delivery requirements already assign the authoritative machine-readable contract to the backend, require a versioned release artifact, and require breaking-change detection. GAM now needs to choose how the contract is generated, rendered, validated, compared, and consumed.

The project is pre-production and has no released OpenAPI contract that constrains the initial toolchain. The selected workflow should remain small enough for the current team while establishing reliable contract ownership.

## Decision
Use a code-first OpenAPI 3.1 workflow generated from the Spring Boot application.

The backend shall use:

- Springdoc's Spring Boot 3 Web MVC integration to generate the contract from controllers, DTOs, validation, security declarations, and explicit OpenAPI annotations;
- Swagger UI as the only initial interactive renderer;
- Spectral with a small repository-owned ruleset for contract quality;
- `oasdiff` for pull-request diffs and breaking-change detection; and
- `openapi-typescript` in the TypeScript frontend to generate compile-time request and response types.

Swagger UI and the live JSON contract shall be readable without authentication at `/api/docs` and `/api/openapi.json`. Interactive request execution shall be enabled in development and disabled for all HTTP methods in production.

The release pipeline shall export `openapi.yaml` from the same definition used by the live documentation and publish it with the matching immutable backend GitHub Release. The generated artifact shall not be committed or manually edited.

The public API base shall remain `/api`; OpenAPI artifact versions shall follow backend release versions rather than introducing a `/v1` URL prefix.

## Alternatives considered

### Option 1: Code-first Springdoc with governed generated artifacts
Pros:
- Keeps routes, Java types, validation constraints, and structural documentation close to implementation.
- Fits the existing Spring Boot application.
- Produces the live documentation and release artifact from one definition.
- Supports generated TypeScript types and automated compatibility checks.

Cons:
- Business meaning, examples, errors, and usage guidance still require deliberate annotations and review.
- Contract generation requires the application or a build lifecycle capable of constructing its web context.
- A code change can affect the contract unexpectedly and must be caught in CI.

### Option 2: Design-first manually maintained OpenAPI
Pros:
- Allows contract review and frontend mocking before backend implementation.
- Makes the API description the direct design artifact.

Cons:
- Introduces a second structure that can diverge from the current controllers and DTOs.
- Requires code generation or runtime validation to keep implementation aligned.
- Adds process weight that has not been justified for the current project size.

### Option 3: Handwritten reference documentation without a governed contract
Pros:
- Minimal tooling.
- Easy to begin with Markdown examples.

Cons:
- Cannot generate dependable frontend types.
- Cannot detect structural breaking changes automatically.
- Becomes stale through manual duplication.
- Does not satisfy the accepted backend-owned contract requirements.

### Option 4: Additional renderer or hosted API portal
Pros:
- May provide a more polished browsing experience.
- Could add search, analytics, or multi-version presentation.

Cons:
- Adds deployment, configuration, and maintenance before Swagger UI has demonstrated a limitation.
- Does not improve the underlying contract by itself.

## Consequences

Positive consequences:
- Frontend Developers can discover the API locally and in production without backend code knowledge.
- Frontend request and response types are generated from an immutable backend contract version.
- API changes become visible and reviewable in pull requests.
- The project maintains one generated machine-readable contract rather than competing documents.
- Additional renderers or testing tools can be evaluated later without changing the OpenAPI source.

Negative consequences:
- Every endpoint change must include deliberate OpenAPI maintenance.
- Existing endpoints must be fully documented before the rollout is complete.
- Current raw Spring `Page` responses and the current extra `ApiErrorDTO.error` field must be aligned with the accepted contract before implementation is complete.
- CI needs a reproducible application-backed contract generation lifecycle.
- Separate repositories still require coordination when a contract change affects the frontend.

## Related requirements

- `REQ-OPENAPI-001`
- `REQ-OPENAPI-002`
- `REQ-OPENAPI-009`
- `REQ-OPENAPI-010`
- `REQ-OPENAPI-011`
- `REQ-WEB-009`
- `REQ-WEB-010`
- `REQ-WEB-011`

## Related diagrams

- [`docs/diagrams/openapi-contract-lifecycle.md`](../diagrams/openapi-contract-lifecycle.md)

## Related videos

- None.
