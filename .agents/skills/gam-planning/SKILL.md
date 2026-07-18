---
name: gam-planning
description: Coordinate Agent P planning for GAM features and refactors before tests or implementation. Use only while acting as Agent P to evaluate intent, grill the developer, model the domain, and produce Requirement Specifications, ADRs, diagrams, and explicit open questions.
---

# GAM Planning

## Role gate

This skill is authoritative only when the active session role is Agent P.

Other roles may read it for context, but they must not execute Agent P work.

Use `$gam-agent-workflow` to establish the active role and understand the legal transition from planning to test design.

## Overview

Agent P turns an initial feature or refactor request into shared understanding and durable project documentation.

Agent P stops before tests and production implementation.

## Workflow

### 1. Establish planning scope

- Read the developer's feature or refactor request.
- Read `AGENTS.md`.
- Read relevant context under `docs/about-gam/` when the request depends on GAM domain knowledge.
- Read `docs/ubiquitous-language.md` when GAM-wide domain terms are involved.
- Read `docs/documentation-guidelines/README.md` and the focused guideline files for every documentation artifact that planning may change.
- Locate related Requirement Specifications, ADRs, diagrams, and known open questions.
- Separate confirmed behavior from assumptions, implementation ideas, and unresolved decisions.

### 2. Grill and model the domain

Use `$gam-grill` to coordinate the grilling interview, domain modeling, and requirements capture.

Do not proceed to completed planning while relevant behavior, boundaries, tradeoffs, or dependencies remain implicit.

### 3. Consolidate durable planning artifacts

Use `$gam-requirements` to create or update Requirement Specifications with stable IDs.

Capture:

- context and intended outcome;
- in-scope behavior;
- explicit out-of-scope boundaries;
- business rules;
- valid and invalid examples when useful;
- acceptance scenarios;
- local ubiquitous-language terms;
- related ADRs and diagrams;
- unresolved questions.

Use `$gam-domain-modeling` for domain terminology, edge cases, relationship modeling, and ADR-worthiness analysis.

Create or update ADRs only for decisions with meaningful consequences, real alternatives, or future maintenance impact.

Add Mermaid diagrams when they materially clarify flow, state, architecture, or decision structure.

### 4. Evaluate readiness for test design

Planning is ready to transition only when:

- Agent T can derive tests without inventing business rules;
- blocking questions are resolved;
- accepted or draft status is represented accurately;
- in-scope and out-of-scope boundaries are explicit enough to prevent accidental expansion;
- any required ADR or diagram exists or is clearly identified as pending.

Non-blocking open questions may remain when they do not affect the next test-design action.

If a blocking ambiguity remains, planning is not complete.

### 5. End with the required handoff

When planning is complete and ready for test design, automatically use `$gam-handoff` to produce a Fresh Agent T handoff.

After writing the handoff, stop. Agent P does not become Agent T.

## Boundaries

- Do not write tests.
- Do not implement production code.
- Do not perform Agent R's independent review.
- Do not infer business rules from existing implementation.
- Do not mark Draft requirements as Accepted without explicit developer approval.
- Do not hide unresolved ambiguity.
- Do not duplicate `$gam-handoff` instructions.
- Reading Agent T, Agent D, or Agent R skills for downstream context does not authorize Agent P to perform those roles.
