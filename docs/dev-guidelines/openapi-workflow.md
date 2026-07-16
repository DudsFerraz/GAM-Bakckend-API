# OpenAPI Workflow

OpenAPI is the externally visible HTTP contract. It describes paths, inputs, outputs, status codes, validation constraints, authentication, errors, and examples. Requirements and ADRs define intended behavior and architecture. Do not infer business rules from a controller or generated schema.

## 1. View the live contract

From the repository root, start the development backend:

```powershell
.\mvnw.cmd -Pdev
```

Then open Swagger UI:

```text
http://localhost:8080/api/docs
```

The raw live contract is:

```text
http://localhost:8080/api/openapi.json
```

If using the frontend development proxy, use its origin instead:

```text
http://<frontend-development-origin>/api/docs
http://<frontend-development-origin>/api/openapi.json
```

In Swagger UI, check the operation summary, security requirements, parameters, request-body requiredness, success response, and expected error responses.

Use **Try it out** only with synthetic development data. Use bearer authorization only for access tokens. Never paste refresh tokens, CSRF tokens, credentials, or production data into Swagger UI.

## 2. Generate the release-style contract

Run:

```powershell
.\mvnw.cmd -Popenapi verify
```

The generated contract is:

```text
target/openapi/openapi.yaml
```

This file is a generated working artifact. Do not commit or manually edit it.

## 3. Review the generated contract

Inspect the generated file when needed:

```powershell
Get-Content target/openapi/openapi.yaml
```

Review all generated quality-check output, including:

- Spectral linting results;
- `oasdiff` comparison results; and
- the rendered Swagger UI when descriptions, examples, tags, or security presentation changed.

The repository CI performs the same OpenAPI governance checks. A failed check must be investigated; do not bypass it by editing generated output.

## 4. Identify breaking changes

Treat these as breaking changes unless deliberately approved:

- removed paths or operations;
- renamed `operationId`s;
- newly required request fields;
- removed or narrowed response fields;
- changed types or formats;
- removed or changed enum values; or
- stricter authentication requirements.

Review every reported diff, including changes not classified as breaking. If the generated contract conflicts with accepted behavior, stop and report the conflict before changing the contract.

Do not publish a manually copied live contract. A release must publish the generated `openapi.yaml` produced from the matching backend release.

## 5. Generate frontend types

For local frontend development, generate types from the live contract:

```powershell
npx openapi-typescript http://localhost:8080/api/openapi.json -o src/api/generated/gam-api.ts
```

When the frontend uses its development proxy, replace the URL with:

```text
http://<frontend-development-origin>/api/openapi.json
```

Use the frontend repository’s generated-output path. Do not edit generated types by hand.
