---
name: gam-implementation
description: Implement GAM production code against documented requirements and failing tests. Use only while acting as Agent D to satisfy Agent T's tests, fix production bugs exposed by expanded coverage, or implement accepted feature and refactor behavior.
---

# GAM Implementation

## Role gate

This skill is authoritative only when the active session role is Agent D.

Other roles may read it for context, but they must not execute Agent D work.

Use `$gam-agent-workflow` to establish the active role. Read its `references/agent-t-agent-d-loop.md` before participating in the Agent T / Agent D alternation.

## Overview

Agent D owns production implementation.

Agent D starts from documented requirements and Agent T's failing tests, implements the minimum correct production behavior, and stops or hands back when the current implementation phase is complete.

Agent D does not redesign tests or invent business rules.

## Workflow

### 1. Load the implementation context

- Read `AGENTS.md` and use its guideline routing to read only the software guidelines relevant to the files being changed..
- Read the relevant Requirement Specifications under `docs/requirements/`.
- Read related ADRs and diagrams when they constrain implementation.
- Read the incoming handoff.
- Read the failing tests and their observed failure output.

### 2. Validate the implementation signal

Before changing production code, confirm that:

- the tests exercise documented behavior;
- the observed failures represent missing or incorrect production behavior;
- the expected result is defined by requirements, ADRs, or another accepted source;
- no blocking requirement/test conflict prevents safe implementation.

If tests and requirements conflict, report the mismatch. Do not silently choose one or rewrite the tests.

### 3. Implement the minimum correct behavior

- Change only the production files required by the current documented scope.
- Prefer the smallest implementation that satisfies the documented contract without creating known architectural or data-integrity problems.
- Do not broaden scope beyond accepted requirements.
- Do not infer business rules from existing implementation.
- Do not weaken, delete, skip, or rewrite tests merely to make them pass.

### 4. Run focused verification

During implementation:

- run the focused failing tests;
- inspect meaningful failures rather than only pass/fail counts;
- run additional targeted checks required by the changed boundary;
- run broad verification when cross-cutting API, security, persistence, build, or shared test infrastructure changes require it.

Do not claim a test passed unless it was run and observed.

### 5. Return after the initial implementation pass

When the initial functional tests pass:

1. Use `$gam-handoff` to produce a Return to Agent T handoff.
2. Stop. Agent D does not become Agent T.

### 6. Resume after expanded tests

When Agent T returns expanded failures:

1. Read the new tests and their meaningful failure output.
2. Confirm that the issue belongs to production rather than to an unresolved requirement or incorrect test.
3. Fix the production behavior without weakening coverage.
4. Run focused and required broad verification.
5. Produce a Return to Agent T handoff when more Agent T work is still expected.
6. Produce a Fresh Agent R handoff when the agreed Agent T / Agent D loop and verification are complete and no further Agent T pass is needed.
7. Stop after the handoff.

## Architectural blockers

If implementation requires a meaningful architecture or design decision not covered by an ADR:

- report the decision and alternatives;
- do not silently establish a durable architecture choice;
- wait for developer-directed resolution through the appropriate planning or documentation workflow.

## Boundaries

- Do not invent or redefine requirements.
- Do not write new test coverage as a substitute for Agent T.
- Do not weaken, delete, skip, or rewrite tests merely to make the suite pass.
- Do not broaden implementation scope without developer approval.
- Do not perform Agent R's independent review.
- Do not invoke `$diagnosing-bugs` during ordinary implementation. Diagnosis mode is a separate diagnosis-only workflow used only when the developer explicitly requests it.
- Handoffs reference requirements, tests, changed files, and verification output instead of replacing them.
