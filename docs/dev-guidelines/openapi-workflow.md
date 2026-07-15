# OpenAPI Developer Workflow

This guide explains when and how a developer should browse, generate, review, and consume GAM's OpenAPI documentation.

> **Implementation status:** The accepted OpenAPI requirements and ADR define the target workflow. The routes and generation profile become executable after the corresponding production and build tooling is implemented; this document is not evidence that the tooling already exists.

This is a practical guide. Normative rules live in the [OpenAPI requirements](../requirements/platform/openapi-and-frontend-api-documentation.md), [OpenAPI documentation guideline](../documentation-guidelines/openapi.md), and [OpenAPI software guideline](../software-guidelines/openapi-documentation.md).

## When to use this workflow

Use the workflow when you:

- create or modify a controller endpoint;
- change a request or response DTO;
- change validation, nullability, formats, enums, status codes, errors, security, pagination, or sorting;
- need to learn how an existing endpoint works;
- need a local contract for frontend development;
- need to review whether a pull request changes the API; or
- prepare a backend release contract.

## 1. Read the owning documentation

Before changing an endpoint:

1. Read its Requirement Specification.
2. Read related ADRs when the change affects architecture or compatibility.
3. Read `docs/software-guidelines/controllers-and-http-api.md`.
4. Read `docs/software-guidelines/openapi-documentation.md`.
5. Resolve missing or contradictory behavior before editing annotations or code.

Do not infer business rules from the current controller or generated schema.

## 2. Run the backend for live documentation

Follow [Start the Backend](running-the-system/start-the-backend.md) to run the development profile and its required services.

Through the supported same-origin frontend development proxy, open:

```text
http://<frontend-development-origin>/api/docs
```

The live JSON contract is available at:

```text
http://<frontend-development-origin>/api/openapi.json
```

When accessing the backend port directly without the `/api`-adding proxy, use the backend-local paths documented by the implemented routing configuration. The public and frontend-facing paths remain `/api/docs` and `/api/openapi.json`.

## 3. Consult an endpoint

In Swagger UI:

1. Find the consumer-facing tag, such as `Members` or `Authentication`.
2. Select the operation by summary or `operationId`.
3. Read its purpose and security requirements.
4. Inspect parameters and request-body requiredness.
5. Inspect success and every expected error response.
6. Compare examples with the relevant Requirement Specification when business behavior matters.

If Swagger and an Accepted Requirement Specification conflict, stop and report the conflict. The requirement governs behavior until the contract is corrected.

## 4. Execute a request in development

Swagger UI request execution is for development only.

1. Obtain CSRF proof through the documented authentication bootstrap when the operation requires it.
2. Use Swagger's bearer authorization control only for an access token.
3. Never paste a refresh token into Swagger. The browser manages the `refreshToken` cookie.
4. Select **Try it out**.
5. Enter only synthetic development data.
6. Select **Execute** and inspect the request URL, status, headers, and body.

Do not use Swagger UI to experiment with real production mutations. Production Swagger UI does not expose request execution.

## 5. Generate and validate the contract locally

The implementation shall provide this stable repository-owned entry point:

```powershell
.\mvnw.cmd -Popenapi verify
```

The profile is responsible for orchestrating any required application lifecycle, exporting the contract, applying the repository-owned Spectral rules, and reporting contract changes. Developers should not replace it with an undocumented sequence of manual downloads.

The generated working artifact belongs under `target/` and must not be committed.

After generation:

1. Confirm the command succeeds.
2. Inspect the generated `openapi.yaml` under the path reported by Maven.
3. Review the Spectral output.
4. Review the `oasdiff` output against the target contract.
5. Open Swagger UI when descriptions, examples, tags, or security presentation changed.

## 6. Interpret the contract diff

Review every reported change, not only changes classified as breaking.

Typical breaking changes include:

- removing a path or operation;
- renaming an `operationId`;
- adding a required request property;
- removing or narrowing a response property;
- changing a field type or format;
- removing an accepted enum value;
- making authentication more restrictive; or
- removing a documented response.

During pre-production, an approved coordinated breaking change may proceed without compatibility code. Record Developer approval and the coordinated frontend update in the pull request. After production begins, follow the accepted compatibility requirement.

## 7. Generate TypeScript types for local frontend work

The frontend may generate types directly from the live development contract:

```powershell
npx openapi-typescript http://<frontend-development-origin>/api/openapi.json -o src/api/generated/gam-api.ts
```

Use the output path owned by the frontend repository. Do not manually edit generated types.

Local generation is for rapid development. A release or production frontend build must use the explicitly pinned `openapi.yaml` from the matching immutable backend GitHub Release.

## 8. Prepare a backend release

The release workflow shall:

1. Generate the contract from the release candidate.
2. Run Spectral.
3. Compare the candidate with the selected previous contract through `oasdiff`.
4. Require handling of every breaking change.
5. Set OpenAPI `info.version` to the backend release version.
6. Export `openapi.yaml`.
7. Publish it as an asset of the matching immutable GitHub Release.

Do not publish from a developer's manually copied live contract.

## Troubleshooting

### Swagger UI is missing an endpoint

Check that the route is part of the public GAM application API, the controller is scanned, and it has not been incorrectly excluded. The initial rollout is incomplete until every frontend-facing endpoint is represented.

### Swagger shows the wrong security requirement

Check the global bearer default and the operation-level public override. Authentication endpoints may still require CSRF proof or browser-managed cookies even when they do not require a bearer token.

### Generated TypeScript is unexpectedly broad

Check requiredness, explicit nullability, schema types, response schemas, and whether a controller exposes a framework type instead of a GAM-owned DTO.

### The contract changed during a refactor

Treat the generated diff as evidence. Determine whether the API really changed or generation leaked an internal rename. Preserve the stable public contract unless a deliberate requirement change authorizes the difference.
