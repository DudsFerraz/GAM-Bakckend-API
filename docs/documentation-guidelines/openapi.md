# Swagger/OpenAPI documentation

## Purpose

OpenAPI documents the externally visible backend API contract. It is the machine-readable reference for endpoints, inputs, outputs, status codes, validation constraints, authentication requirements, error responses, and examples.

OpenAPI does not replace Requirement Specifications. Requirements define expected behavior and business rules; OpenAPI expresses the HTTP contract through which API consumers observe that behavior.

## Source and generated forms

GAM uses a code-first OpenAPI 3.1 workflow:

```text
Spring controllers, DTOs, validation, security, and OpenAPI annotations
    -> Springdoc-generated OpenAPI contract
        -> live JSON contract
        -> Swagger UI
        -> CI validation and diff
        -> release openapi.yaml
        -> generated frontend TypeScript types
```

The backend definition is authoritative. The live JSON and exported YAML are serializations of the same generated contract. Do not manually maintain a competing OpenAPI file.

## Relationship to project documentation

When an API field or operation is governed by a Requirement Specification, its OpenAPI documentation must reflect the externally visible rule and should reference the owning requirement when that helps traceability.

Use:

- Requirement Specifications for behavior and business rules;
- ADRs for consequential contract-generation and governance decisions;
- OpenAPI for exact HTTP operations and schemas; and
- `docs/api/README.md` for cross-cutting integration guidance and links.

If OpenAPI conflicts with an Accepted Requirement Specification, report the conflict and treat the requirement as the behavioral source of truth until the contract is corrected.

## Operation documentation rule

Every frontend-facing operation must have:

- a globally unique explicit `operationId`;
- one primary consumer-facing tag;
- a concise summary and useful purpose;
- accurate security requirements;
- documented path, query, header, cookie, and body inputs;
- complete success response schemas;
- every expected error status and common error schema;
- consumer-visible validation constraints; and
- realistic synthetic examples.

Document preconditions, side effects, idempotency, retry behavior, recommended use, discouraged use, and related endpoints when they are relevant. Do not add empty boilerplate merely to satisfy a template.

## Operation identifiers and tags

Write `operationId` values explicitly in lower camel case using an action and resource:

```text
createMember
getMember
searchMembers
approveMembershipSolicitation
assignAccountRole
```

Do not rely on Java method names to become stable identifiers implicitly.

Group operations with consumer-facing tags such as:

```text
Authentication
Accounts
Members
Membership Solicitations
Events
Locations
Presences
RBAC
```

Do not expose Java package, controller, use-case, or persistence names as the documentation hierarchy.

## Schema rule

Schemas must express the actual consumer-visible contract, including:

- required versus optional properties;
- whether explicit `null` is accepted;
- string length and pattern constraints;
- numeric ranges;
- collection size constraints;
- UUID, date, and date-time formats;
- enum values and meanings; and
- realistic examples.

An optional property is not automatically nullable. In OpenAPI 3.1, represent nullability through the schema type while using the owning object's `required` list for presence.

Bean Validation contributes structural constraints, but annotations must add business meaning, examples, errors, and usage guidance that cannot be inferred from Java types.

## Security documentation

Protected endpoints use the global bearer security requirement unless an operation explicitly overrides it.

Public endpoints must explicitly declare that no bearer credential is required. Public routing does not remove endpoint-specific validation of credentials, cookies, CSRF proof, or inputs.

Authentication operations must document the accepted browser contract, including:

- `X-XSRF-TOKEN` where required;
- browser-managed `refreshToken` cookie behavior for refresh and logout;
- CSRF bootstrap response and cookie behavior; and
- the fact that refresh tokens are not entered into Swagger authorization controls.

## Error documentation

Every JSON error response references the common API error envelope:

```json
{
  "timestamp": "2026-07-14T17:30:00Z",
  "status": 404,
  "code": "RESOURCE_NOT_FOUND",
  "message": "Member not found with the supplied identifier.",
  "details": {
    "resource": "Member",
    "identifier": "0190d5d4-52b3-7d30-a8d3-64b70d6c3142"
  }
}
```

Use `code`, not `message`, as the stable machine-readable discriminator. Document the possible error codes and the structured `details` content relevant to each operation.

Do not add a redundant HTTP reason phrase under an `error` property.

## Pagination, sorting, and filtering

Paged operations must document:

- zero-based `page`, defaulting to `0`;
- `size`, defaulting to `20` and limited to `100`;
- repeatable `sort=field,direction` parameters;
- allowed sort fields and default ordering; and
- the GAM-owned paged response envelope.

Document filtering separately for each endpoint. Do not imply that the presence of a shared search DTO makes every field or comparison available for every resource.

## Common data representations

Use these representations consistently:

- UUID: string with `format: uuid`, treated as opaque;
- calendar date: `YYYY-MM-DD` with `format: date`;
- absolute timestamp: RFC 3339 UTC ending in `Z` with `format: date-time`; and
- enum: documented uppercase string values, never ordinals.

Do not expose a timezone-less date-time.

## Example policy

Examples must be realistic enough to teach the contract but entirely synthetic. Do not include:

- real Member, Account, or applicant data;
- access, refresh, or CSRF token values;
- credentials;
- production hostnames; or
- identifiers copied from production.

Use obvious placeholders for security-sensitive values.

## Interactive documentation and artifacts

The public documentation routes are:

```text
/api/docs
/api/openapi.json
```

Reading them does not require authentication. Swagger UI request execution is enabled in development and disabled for every HTTP method in production.

The release pipeline exports `openapi.yaml` from the same generated definition. It is a release artifact, not a manually edited or committed source file.

## Documentation quality gates

OpenAPI changes are reviewed through:

- contract generation from the application;
- Spectral linting with the repository-owned ruleset;
- `oasdiff` comparison against the target contract; and
- review of the rendered Swagger UI when presentation or examples change.

See the [OpenAPI software guideline](../software-guidelines/openapi-documentation.md) for mandatory endpoint-change rules, the [compatibility baseline lifecycle](../software-guidelines/openapi-documentation.md#7-compatibility-baseline-lifecycle) for agent-facing maintenance rules, and the [OpenAPI developer workflow](../dev-guidelines/openapi-workflow.md#5-maintain-the-compatibility-baseline) for the human step-by-step procedure.

## Related documentation

- [OpenAPI and Frontend API Documentation requirements](../requirements/platform/openapi-and-frontend-api-documentation.md)
- [ADR-0008: Generate and Govern OpenAPI from Backend Code](../decisions/0008-generate-and-govern-openapi-from-backend-code.md)
- [Frontend API Guide](../api/README.md)
