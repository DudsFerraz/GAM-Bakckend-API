# Documentation Guidelines

This project uses lightweight documentation as code.

The goal is to make requirements, architecture decisions, diagrams, API behavior, and implementation rationale clear enough for both humans and LLM agents.

Documentation must be simple, versioned, easy to review in pull requests, and stored in the GitHub repository.

---

## Core decisions

This project uses:

* Requirement Specifications
* ADRs: Architecture Decision Records
* Mermaid diagrams
* Swagger/OpenAPI for backend API documentation
* Videos only as supplemental explanation

---

## Documentation language

Use this vocabulary consistently:

* Requirement Specification: a Markdown document under `docs/requirements/` that describes expected behavior and business rules for one feature, concept, or domain rule group.
* Requirement: an individual rule inside a Requirement Specification, identified by a stable `REQ-<AREA>-<NUMBER>` ID.
* ADR: an Architecture Decision Record under `docs/decisions/` that documents an architecture or design decision.
* Diagram: a Mermaid diagram that supports a Requirement Specification, ADR, or API behavior description.
* OpenAPI contract: the externally visible backend API contract.

---

## Repository structure

```text
docs/
  documentation-guidelines/

  requirements/
    common/
      name.md
    users/
      create-user.md

  decisions/
    0001-use-adrs.md
    0002-use-mermaid.md
    0003-video-documentation-policy.md

  diagrams/
    name-validation-flow.md
    backend-overview.md

  api/
    openapi.md

  testing/
    traceability/
      name-test-matrix.md
```

---

## Guideline files

Read only the documentation guideline files relevant to the work:

* Documentation overview, vocabulary, and general rules: `docs/documentation-guidelines/README.md`
* Requirements: `docs/documentation-guidelines/requirements.md`
* ADRs: `docs/documentation-guidelines/adrs.md`
* Mermaid diagrams: `docs/documentation-guidelines/diagrams.md`
* Swagger/OpenAPI documentation: `docs/documentation-guidelines/openapi.md`
* Video documentation: `docs/documentation-guidelines/videos.md`
* LLM agent instructions: `docs/documentation-guidelines/llm-agents.md`
* Agent implementation workflow: `docs/documentation-guidelines/agent-workflow.md`
* Source of truth priority: `docs/documentation-guidelines/source-of-truth.md`

---

## General documentation rules

1. Requirements must be written before implementation or testing whenever possible.
2. Business rules must not be inferred from the current implementation.
3. If a requirement is missing, unclear, or contradictory, the agent or developer must stop and ask for clarification.
4. Requirements must use stable IDs.
5. Requirements must include valid and invalid examples when applicable.
6. Architecture and design decisions must be documented as ADRs.
7. Diagrams must be written using Mermaid.
8. Videos may be used only as additional explanation.
9. Videos must be linked as YouTube videos inside the official documentation.
10. Video documentation must not replace written requirements, ADRs, or diagrams.
