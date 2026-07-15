# OpenAPI Documentation Guidelines

## 1. Purpose

This guideline makes OpenAPI maintenance mandatory whenever production code changes the externally visible HTTP API. OpenAPI documentation is part of endpoint implementation, not deferred cleanup.

Read the owning Requirement Specifications, relevant ADRs, the controller guideline, and `docs/documentation-guidelines/openapi.md` before changing an endpoint contract.

## 2. Changes that require an OpenAPI update

Review and update OpenAPI documentation in the same work whenever a change creates or modifies:

- a controller route or HTTP method;
- an `operationId`, tag, summary, or operation purpose;
- endpoint authentication or authorization;
- a path, query, header, cookie, or request-body input;
- a request or response DTO;
- requiredness, nullability, validation, format, enum, or example data;
- a success or error status code;
- the common error envelope or an operation-specific error code;
- pagination, sorting, or filtering behavior;
- response headers such as `Location`; or
- externally visible side effects, idempotency, retry, or deprecation behavior.

A refactor that provably leaves the generated contract unchanged still requires generation and diff review when it touches an API boundary.

## 3. Controller and DTO responsibilities

Controllers and DTOs must provide enough explicit metadata for the generated contract to meet `REQ-OPENAPI-003` and `REQ-OPENAPI-004`.

Use Springdoc/OpenAPI annotations for information that Spring cannot infer reliably, including:

- explicit stable `operationId` values;
- operation purpose and relevant usage guidance;
- tags;
- security overrides;
- expected responses and shared error schemas;
- realistic examples; and
- semantic constraints or descriptions beyond Bean Validation.

Do not duplicate domain rules as unrelated prose. Keep the Requirement Specification authoritative and make the OpenAPI schema reflect the externally visible part of the rule.

Do not expose persistence entities or persistence property names merely because generation tooling can discover them.

## 4. Stable consumer contract

Treat these as public contract elements:

- paths and HTTP methods;
- `operationId` values;
- security requirements;
- parameter names and locations;
- request and response schemas;
- required and nullable properties;
- status codes and response headers;
- error codes and structured details;
- enum values;
- pagination and sort fields; and
- field formats and validation constraints.

During pre-production, an approved coordinated change may replace an unreleased contract without compatibility code. It must still be visible in `oasdiff` and coordinated with the frontend. After production begins, follow `REQ-WEB-010`.

## 5. Definition of done for endpoint work

Endpoint work is not complete until all applicable items are satisfied:

1. The owning Requirement Specification covers the intended behavior.
2. The controller and DTO annotations produce the intended OpenAPI contract.
3. Every operation has a stable explicit `operationId`, tag, purpose, security, inputs, outputs, errors, and synthetic examples.
4. Consumer-visible validation and nullability are accurate.
5. The local generated contract has been inspected.
6. Swagger UI has been checked when presentation, security instructions, or examples changed.
7. The repository-owned OpenAPI generation command succeeds.
8. Spectral passes.
9. The `oasdiff` report has been reviewed.
10. Any breaking change has been handled under the accepted compatibility policy.
11. Frontend type impact has been considered and coordinated when the contract changed.

Do not merge endpoint work with an undocumented follow-up promise.

## 6. Generated artifact rules

Do not edit or commit `openapi.yaml` as a source file. It is generated for review and published as an immutable backend release artifact.

The live JSON contract, Swagger UI, CI contract, and release YAML must derive from the same backend definition.

Do not make CI pass by weakening Spectral rules or excluding an endpoint without an accepted reason. Change a quality rule only when the project documentation standard itself changes.

## 7. Review guidance

Reviewers must evaluate both the code diff and the generated contract diff.

Check especially for:

- accidentally exposed framework or persistence details;
- missing error responses;
- security defaults that incorrectly make an endpoint appear public or protected;
- required/optional/nullability drift;
- new response enum values that can break exhaustive frontend handling;
- undocumented sort fields;
- examples containing sensitive or real data; and
- `operationId` churn caused by Java renames.

## Related documentation

- [OpenAPI documentation guideline](../documentation-guidelines/openapi.md)
- [Controllers and HTTP API Guidelines](controllers-and-http-api.md)
- [OpenAPI developer workflow](../dev-guidelines/openapi-workflow.md)
- [OpenAPI and Frontend API Documentation requirements](../requirements/platform/openapi-and-frontend-api-documentation.md)
