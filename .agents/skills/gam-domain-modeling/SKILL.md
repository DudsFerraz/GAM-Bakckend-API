---
name: gam-domain-modeling
description: Build and sharpen the GAM domain model using project documentation standards. Use when clarifying domain terminology, creating or updating Requirement Specifications, deciding whether an ADR is warranted, resolving glossary or requirement ambiguity, or when another skill needs project-specific domain modeling before implementation.
---

# GAM Domain Modeling

## Overview

Actively sharpen the project's domain language as requirements and designs evolve. Challenge vague terms, force concrete scenarios, and record durable decisions when they crystallize.

Project documentation is the source of truth. Read `docs/documentation-guidelines.md` before creating or changing Requirement Specifications, ADRs, diagrams, or glossary material.

## Workflow

1. Identify the domain area and existing documentation.
   - Read relevant files under `docs/requirements/`.
   - Read related ADRs under `docs/decisions/` when architecture or design choices are involved.
   - Read `docs/documentation-guidelines.md` before writing project documentation.
2. Challenge fuzzy or overloaded language.
   - Ask whether a term means one domain concept or another when ambiguity matters.
   - Propose one canonical term and list discouraged alternatives when useful.
3. Stress-test the model with concrete scenarios.
   - Invent edge cases that expose unclear boundaries, lifecycle rules, permissions, ownership, or invalid states.
   - Prefer scenarios that can become acceptance scenarios or examples in a Requirement Specification.
4. Separate domain truth from implementation facts.
   - Use code to detect contradictions or hidden behavior.
   - Do not treat existing implementation as the source of truth for business rules unless project documentation explicitly says so.
5. Capture durable outcomes in the correct project artifact.
   - Use Requirement Specifications for business behavior, rules, examples, and acceptance scenarios.
   - Use ADRs for architecture or design decisions with meaningful tradeoffs or future maintenance impact.
   - Use a Requirement Specification `Glossary` section for feature-specific terms.
   - Create or update a broader glossary only if the project already has one or the user asks for one.

## Requirement Specifications

When a domain rule, term, valid example, invalid example, or acceptance scenario becomes clear, offer to update the relevant Requirement Specification under `docs/requirements/`.

Follow the structure and wording rules in `docs/documentation-guidelines.md`:

- Use stable requirement IDs.
- Prefer clear, testable statements.
- Include valid and invalid examples when applicable.
- Keep test implementation details out of Requirement Specifications.
- Preserve open questions instead of guessing.

## ADRs

Offer an ADR only when all are true:

1. The decision has meaningful consequences or future maintenance impact.
2. A future maintainer would need to know why the choice was made.
3. There were real alternatives or tradeoffs.

When creating an ADR, use `docs/decisions/` and the ADR template from `docs/documentation-guidelines.md`.

## Conflict Handling

If this skill, another skill, existing code, or external community guidance conflicts with project documentation:

1. Report the conflicting sources.
2. State which source should currently win.
3. Explain the rationale.
4. Ask whether to update the relevant Requirement Specification, ADR, guideline, or skill when the conflict should be resolved durably.
