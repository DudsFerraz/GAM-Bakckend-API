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

## 5. Maintain the compatibility baseline

`OPENAPI_BASELINE_URL` is the repository Actions variable that identifies the most recent accepted immutable release contract. OpenAPI Governance uses it as the `oasdiff` comparison target and fails closed when it is absent during an ordinary pull-request, branch, or release run.

Use this lifecycle:

| Situation | Required action |
| --- | --- |
| No accepted release contract exists | Bootstrap the initial baseline once |
| A pull request or branch build runs | Do not change the baseline |
| A release fails or is rejected | Do not change the baseline |
| An accepted release passes governance and publishes `openapi.yaml` | Promote that release artifact as the new baseline |

Never use a pull-request artifact, mutable branch URL, failed release, or unaccepted candidate as the compatibility baseline.

### Bootstrap the initial baseline once

Use this procedure only when the project has no prior release contract:

1. Merge the OpenAPI governance workflow into the repository default branch. The first push may fail OpenAPI Governance because `OPENAPI_BASELINE_URL` does not exist yet; that is expected during bootstrap.
2. Confirm under repository **Settings** -> **Secrets and variables** -> **Actions** -> **Variables** that `OPENAPI_BASELINE_URL` is absent.
3. Open **Actions**, select **OpenAPI Governance**, and click **Run workflow**.
4. Select the default branch, enable `bootstrap_baseline`, and enter a new immutable `baseline_tag`, such as `openapi-baseline-v1`. The tag may contain only letters, numbers, dots, underscores, and hyphens.
5. Start the run and confirm that `govern-openapi` and `publish-initial-openapi-baseline` succeed.
6. Confirm that the new GitHub Release contains `openapi.yaml`.
7. Copy the asset URL reported in the workflow summary. It has this form:

   ```text
   https://github.com/OWNER/REPOSITORY/releases/download/openapi-baseline-v1/openapi.yaml
   ```

8. Return to repository **Settings** -> **Secrets and variables** -> **Actions** -> **Variables**, create `OPENAPI_BASELINE_URL` with that URL, and save it.
9. Run OpenAPI Governance again with `bootstrap_baseline` disabled and `baseline_tag` empty. Confirm that baseline download and `oasdiff` succeed.

Do not bootstrap again after the variable exists. Do not delete, move, or reuse the bootstrap tag or replace its release asset.

### Advance the baseline after an accepted release

Repeat this promotion after every accepted backend release, but only after its governance run succeeds:

1. Publish the normal immutable backend release tag, such as `1.2.0`.
2. Confirm that OpenAPI Governance compared the release candidate with the previously configured baseline and succeeded.
3. Confirm that the matching GitHub Release contains its generated `openapi.yaml`.
4. Construct or copy the immutable asset URL:

   ```text
   https://github.com/OWNER/REPOSITORY/releases/download/1.2.0/openapi.yaml
   ```

5. Open repository **Settings** -> **Secrets and variables** -> **Actions** -> **Variables**.
6. Edit `OPENAPI_BASELINE_URL`, replace its value with the new release asset URL, and save it.
7. Leave that value unchanged until a later accepted release becomes the next baseline.

Do not update the variable before the release check finishes. Doing so could compare the candidate with itself and hide a breaking change.

Advancing the baseline is required because `oasdiff` only knows the comparison contract it receives. If release 1 adds `/reports` but the baseline still predates that operation, a later accidental removal of `/reports` may not be detected.

For a private repository, the governance workflow must authenticate same-repository release downloads. Use an authenticated mechanism such as `gh release download`; unauthenticated `curl` cannot be relied upon for a private release asset.

See `REQ-OPENAPI-011` and the [OpenAPI software guideline](../software-guidelines/openapi-documentation.md#7-compatibility-baseline-lifecycle) for the governing rules.

## 6. Generate frontend types

For local frontend development, generate types from the live contract:

```powershell
npx openapi-typescript http://localhost:8080/api/openapi.json -o src/api/generated/gam-api.ts
```

When the frontend uses its development proxy, replace the URL with:

```text
http://<frontend-development-origin>/api/openapi.json
```

Use the frontend repository’s generated-output path. Do not edit generated types by hand.
