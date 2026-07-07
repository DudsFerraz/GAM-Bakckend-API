---
name: gam-test-design
description: Derive GAM test suites from requirements and testing guidelines. Use when writing, planning, reviewing, or expanding functional, structural, unit, integration, API, security, persistence, or regression tests before or after implementation.
---

# GAM Test Design

## Overview

Use this skill to design tests from documented behavior. Tests must protect the intended domain contract, not merely preserve accidental current implementation behavior.

## Workflow

1. Read `docs/software-guidelines/testing.md`.
2. Read the related Requirement Specification under `docs/requirements/`.
3. Read related ADRs or diagrams when they affect behavior, architecture, or flow.
4. Stop and ask for clarification when the requirement is incomplete, ambiguous, or missing important constraints.
5. Derive functional tests first:
   - Map equivalence classes.
   - Map boundary values.
   - Cover valid behavior, invalid behavior, and error outputs.
   - Avoid duplicate cases that produce the same behavioral signal.
6. Derive structural tests when source code is available and the functional contract is clear:
   - Map decisions and individual boolean conditions.
   - Cover true and false outcomes for relevant conditions.
   - Apply loop boundary adequacy when loops matter.
7. Choose the right execution level:
   - Unit tests for isolated domain/application behavior.
   - Integration, API, security, or persistence tests when behavior crosses boundaries.
8. Use project test organization rules:
   - Prefer custom test annotations over raw `@Tag`.
   - Use clear `@DisplayName` values.
   - Use `@Nested` only when it improves readability.
   - Use parameterized tests for repeated behavior over varied values.

## Quality Gates

- Tests must trace to requirement behavior or an explicitly documented defect.
- Tests must not encode accidental implementation behavior as business truth.
- API/security tests must distinguish authentication failures from authorization failures.
- Shared API test support, authentication, authorization, token behavior, or security configuration changes require broad verification such as full `mvn verify`.
- If a test reveals a production defect, fix production behavior instead of weakening the test.
