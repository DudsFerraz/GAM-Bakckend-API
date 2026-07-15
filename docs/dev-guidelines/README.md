# Developer Guidelines

This folder contains practical guides for human developers working on the GAM API.

Use these documents for day-to-day development tasks such as running the backend, starting Docker services, choosing Maven commands, inspecting dependencies, and preparing clear prompts for agent-assisted work.

These files are not LLM system instructions, developer instructions, agent policies, or source-of-truth project rules. Agent-facing workflow and policy documentation lives under `docs/documentation-guidelines/` and the repository agent instruction files.

## Guides

- [Running the System](running-the-system/README.md): how to run the backend locally, manage Docker Compose, execute Maven commands, inspect dependencies, and run OWASP dependency-check.
- [Prompting the Agent Workflow](prompting-agent-workflow.md): examples for human developers who want to start agent sessions for planning, test design, implementation, or review.
- [OpenAPI Developer Workflow](openapi-workflow.md): when and how to browse Swagger UI, generate and validate the contract, review changes, and generate frontend TypeScript types.
- [Frontend–Backend Integration](front-back-integration.md): practical same-origin browser, authentication, CSRF, and API integration guidance.

## Useful Future Guides

The next simple guides that would help developers are:

- Local environment setup: installing JDK 21, Maven Wrapper usage, Docker Desktop, IDE settings, and `JAVA_HOME`.
- Database access: connecting to the local PostgreSQL container, reading migration state, and resetting local data safely.
- Test troubleshooting: common failures from Testcontainers, Docker, ports, profiles, and Java version mismatches.
- Dependency maintenance: updating Spring Boot, Flyway, MapStruct, Lombok, and reviewing dependency-check findings.
- Release checklist: commands and manual checks before tagging or deploying.
