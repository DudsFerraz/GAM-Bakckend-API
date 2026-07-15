# OpenAPI Contract Lifecycle

This diagram shows how one backend OpenAPI definition supports local exploration, contract governance, release publication, and frontend type generation without creating a second manually maintained contract.

```mermaid
flowchart TD
    Code["Spring controllers, DTOs, validation, security, and OpenAPI annotations"] --> Springdoc["Springdoc OpenAPI 3.1 generation"]

    Springdoc --> LiveJson["Live contract\n/api/openapi.json"]
    LiveJson --> Swagger["Swagger UI\n/api/docs"]
    Swagger --> DevTry["Development request execution"]
    Swagger --> ProdBrowse["Production read-only browsing"]

    Springdoc --> CiContract["CI-generated contract"]
    CiContract --> Spectral["Spectral quality rules"]
    CiContract --> Diff["oasdiff against target contract"]
    Spectral --> ReleaseGate{"Contract checks pass?"}
    Diff --> ReleaseGate

    ReleaseGate -- No --> Block["Block until corrected or explicitly handled"]
    ReleaseGate -- Yes --> Release["Versioned backend GitHub Release"]
    Release --> Artifact["Immutable openapi.yaml"]
    Artifact --> Typegen["openapi-typescript"]
    Typegen --> Frontend["Generated frontend API types"]

    LiveJson -. "local type generation" .-> Typegen
```

## Related documentation

- [OpenAPI and Frontend API Documentation requirements](../requirements/platform/openapi-and-frontend-api-documentation.md)
- [ADR-0008: Generate and Govern OpenAPI from Backend Code](../decisions/0008-generate-and-govern-openapi-from-backend-code.md)
- [OpenAPI documentation guideline](../documentation-guidelines/openapi.md)
