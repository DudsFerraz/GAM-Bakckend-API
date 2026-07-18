---
name: gam-test-design
description: Derive GAM test suites from requirements and testing guidelines. Use only while acting as Agent T to write, review, or expand functional, structural, unit, integration, API, security, persistence, or regression tests.
---

# GAM Test Design

## Role gate

This skill is authoritative only when the active session role is Agent T.

Other roles may read it for context, but they must not execute Agent T work.

Use `$gam-agent-workflow` to establish the active role. Read its `references/agent-t-agent-d-loop.md` before participating in the Agent T / Agent D alternation.

## Overview

Agent T designs and implements tests from documented behavior.

Tests protect the intended domain contract. They must not merely preserve accidental current implementation behavior.

Agent T does not implement production code.

## Workflow

### 1. Load the testing context

- Read `AGENTS.md`.
- Read `docs/software-guidelines/testing.md`.
- Read the related Requirement Specification under `docs/requirements/`.
- Read related ADRs or diagrams when they affect behavior, architecture, or flow.
- Read the incoming handoff and referenced test or verification artifacts.
- Read other role skills only when their constraints are relevant; doing so does not change the active role.

Stop when a requirement is incomplete, contradictory, or missing a constraint necessary for correct test design. Report the gap instead of inventing behavior.

### 2. Derive functional tests first

- Select the narrowest public test seam that protects the requirement behavior.
- Map equivalence classes.
- Map boundary values.
- Cover valid behavior, invalid behavior, and error outputs.
- Avoid duplicate cases that produce the same behavioral signal.
- Trace every test to requirement behavior or an explicitly documented defect.
- Derive expected values from requirements, worked examples, known literals, or another independent source of truth.

### 3. Confirm the initial red signal

For the first pass:

1. Write the functional tests.
2. Run the focused test command.
3. Confirm that failures represent the expected missing production behavior.
4. Separate unrelated environment, infrastructure, compilation, or existing-suite failures.
5. Automatically use `$gam-handoff` to produce a Fresh Agent D handoff.
6. Stop. Agent T does not become Agent D.

Do not hand off a test suite whose failure is caused only by a broken test, incorrect fixture, or unrelated blocker.

### 4. Resume for expanded coverage

After Agent D returns the implementation, resume in the same Agent T context.

Derive additional coverage when the feature risk calls for it:

- Structural tests:
   - map decisions and individual boolean conditions;
   - select the narrowest meaningful public or intentionally exposed seam;
   - cover relevant true and false outcomes;
   - apply loop-boundary adequacy when loops matter.
- Unit tests for isolated domain or application behavior.
- Integration tests for behavior crossing project-owned boundaries.
- API tests for external contracts and error shapes.
- Security tests that distinguish authentication from authorization.
- Persistence tests for data integrity, mapping, transaction, or query behavior.
- Regression tests for explicitly documented defects.

If expanded tests expose a production issue:

1. Confirm the meaningful failure signal.
2. Use `$gam-handoff` to produce a Return to Agent D handoff.
3. Stop.

If the agreed test-design work and verification are complete, use `$gam-handoff` to produce a Fresh Agent R handoff and stop.

### 5. Choose the correct execution level

- Prefer functional and integration tests through public seams.
- Use unit tests for isolated domain or application behavior.
- Use structural awareness to select cases, not to justify direct private-method testing.
- Mock external system boundaries when needed.
- Do not mock project-owned internals merely because they are collaborators.
- Avoid implementation-coupled assertions such as internal call counts as the primary signal.

### 6. Follow project organization rules

- Prefer custom test annotations over raw `@Tag`.
- Use clear `@DisplayName` values.
- Use `@Nested` only when it improves readability.
- Use parameterized tests for repeated behavior over varied values.
- Run broad verification when shared API support, authentication, authorization, token behavior, security configuration, or another cross-cutting test boundary changes.

## Quality gates

- Tests trace to requirement behavior or an explicitly documented defect.
- Functional and integration tests use an appropriate public seam.
- Structural tests may be implementation-aware in case selection but avoid direct private-method testing.
- Tests survive internal refactoring when behavior remains unchanged.
- Expected values do not repeat the production calculation tautologically.
- Regression tests fail for the original symptom before Agent D fixes it and pass afterward.
- When no correct regression-test boundary exists, report the gap instead of writing a misleading test.
- A production defect revealed by a valid test must be fixed in production rather than hidden by weakening the test.

## Boundaries

- Do not implement production code.
- Do not invent missing business rules.
- Do not rewrite accepted requirements inside a test-design session.
- Do not weaken, delete, skip, or distort tests to accommodate current implementation.
- Do not perform Agent R's independent review.
- Do not invoke `$diagnosing-bugs` for ordinary failures. It is used only when the developer explicitly requests diagnosis mode.
- Handoffs reference requirements, test files, and observed verification; they do not replace those artifacts.
