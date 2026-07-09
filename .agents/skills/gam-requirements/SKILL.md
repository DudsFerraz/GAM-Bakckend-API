---
name: gam-requirements
description: Create and refine GAM Requirement Specifications from client or developer intent. Use when clarifying feature behavior, translating rough requirements into docs/requirements files, adding requirement IDs, examples, acceptance scenarios, ubiquitous language terms, open questions, or preparing requirements before tests or implementation.
---

# GAM Requirements

## Overview

Use this skill to turn rough intent into project-standard Requirement Specifications. Requirements define expected behavior and business rules; they must not contain test implementation details or merely describe the current code.

## Workflow

1. Read `docs/documentation-guidelines/README.md` and `docs/documentation-guidelines/requirements.md`.
2. Locate related requirements under `docs/requirements/`, related ADRs under `docs/decisions/`, and related diagrams under `docs/diagrams/` when they exist.
3. Clarify missing or ambiguous business rules before writing requirements.
4. Use `$gam-domain-modeling` when terminology, domain boundaries, or ubiquitous language choices are unclear.
5. When invoked after grilling, convert resolved answers into requirements, examples, acceptance scenarios, ubiquitous language terms, diagrams, or ADR links; convert unresolved answers into open questions.
6. Write or update the Requirement Specification using the project template:
   - Status
   - Context
   - Ubiquitous Language
   - Functional requirements with stable `REQ-<AREA>-<NUMBER>` IDs
   - Valid and invalid examples when applicable
   - Acceptance scenarios
   - Diagrams, open questions, out-of-scope boundaries, related ADRs, and related videos when relevant
7. Preserve uncertainty as open questions instead of inventing business rules.

## Quality Gates

- Requirements must be clear, testable, and written before implementation or testing whenever possible.
- Requirement Specification `Ubiquitous Language` sections must not repeat or redefine global terms, aliases, synonyms, translations, or legacy names from `docs/ubiquitous-language.md`; they should contain local terms only.
- Synonyms, translations, and legacy names should be recorded as aliases to avoid when they compete with a canonical term.
- Requirements must describe expected behavior and business rules, not test classes, test methods, or implementation details.
- Requirements should capture explicit scope exclusions in `Out of scope` when they prevent scope creep or clarify deferred behavior.
- Do not infer business rules from existing implementation.
- If a requirement changes meaning significantly, create a new requirement ID rather than reusing the old one.
- If a decision has architecture consequences, ask whether an ADR should be created or updated.
