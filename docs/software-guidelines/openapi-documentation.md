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

## 7. Compatibility baseline lifecycle

`OPENAPI_BASELINE_URL` identifies the most recent accepted immutable release contract. It is a repository Actions variable, not a secret, and ordinary governance runs fail closed when it is absent.

### Establish the first baseline

Use this bootstrap exactly once, when no earlier release contract exists:

1. Merge the OpenAPI governance workflow into the default branch.
2. Confirm that `OPENAPI_BASELINE_URL` is not configured and choose a new tag containing only letters, numbers, dots, underscores, and hyphens, such as `openapi-baseline-v1`.
3. In GitHub, open **Actions**, select **OpenAPI Governance**, and choose **Run workflow**.
4. Select the default branch, enable `bootstrap_baseline`, enter the new immutable `baseline_tag`, and start the run.
5. Confirm that `govern-openapi` and `publish-initial-openapi-baseline` succeed and that the resulting GitHub Release contains `openapi.yaml`.
6. Copy the URL reported in the workflow summary. It has this form:

   ```text
   https://github.com/OWNER/REPOSITORY/releases/download/openapi-baseline-v1/openapi.yaml
   ```

7. Open repository **Settings** -> **Secrets and variables** -> **Actions** -> **Variables**, create `OPENAPI_BASELINE_URL` with that URL, and save it.
8. Run OpenAPI Governance again with `bootstrap_baseline` disabled. Confirm that baseline download and `oasdiff` both succeed.

Do not rerun the bootstrap, reuse its tag, move the tag, replace its asset, or configure a mutable branch URL.

### Promote an accepted release

The compatibility baseline must advance after every accepted backend release:

1. Publish the normal immutable backend release tag.
2. Let OpenAPI Governance compare the release candidate with the currently configured baseline.
3. Confirm that the release passes and publishes its matching `openapi.yaml` asset.
4. Update `OPENAPI_BASELINE_URL` to that new release asset URL.
5. Leave that URL unchanged until a later release passes and becomes the next accepted baseline.

Never promote a pull-request artifact, failed release, mutable branch contract, or candidate before its compatibility result has been accepted. Do not update the variable before the release check, because that would compare the candidate with itself.

Advancing the baseline is necessary because `oasdiff` can only detect removals relative to the contract it receives. If release 1 adds `/reports` but the variable still points to a contract from before `/reports` existed, a later accidental removal of `/reports` can be missed.

For a private repository, the baseline fetch must authenticate without exposing a token to an arbitrary external URL. Prefer an authenticated same-repository release download, such as `gh release download`, rather than unauthenticated `curl` against a private release asset.

## 8. Review guidance

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
- [Compatibility baseline lifecycle](#7-compatibility-baseline-lifecycle)
- [OpenAPI developer workflow](../dev-guidelines/openapi-workflow.md#5-maintain-the-compatibility-baseline)
- [OpenAPI and Frontend API Documentation requirements](../requirements/platform/openapi-and-frontend-api-documentation.md)
