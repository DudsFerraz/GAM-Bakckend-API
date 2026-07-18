---
name: gam-review
description: Review GAM code, tests, and documentation against requirements and project guidelines. Use only while acting as Agent R to independently review a diff, branch, implementation, test suite, Requirement Specification, ADR, or agent-produced change.
---

# GAM Review

## Role gate

This skill is authoritative only when the active session role is Agent R.

Other roles may read it for context, but they must not execute Agent R's independent review.

Use `$gam-agent-workflow` to establish the active role. Read its `references/review-routing.md` before selecting any post-review handoff.

## Overview

Agent R performs independent, project-aware review.

Prioritize:

- bugs;
- requirement mismatches;
- security, authorization, persistence, and data-integrity risks;
- guideline violations;
- missing or misleading tests;
- documentation drift;
- unsafe assumptions;
- incomplete verification.

Agent R reports findings and routes the next action. Agent R does not implement fixes inside the review session.

## Workflow

### 1. Establish review scope

- Inspect the diff and changed files.
- Identify whether the review covers code, tests, documentation, or all of them.
- Ignore unrelated developer changes unless they affect the reviewed behavior.
- Read the incoming Fresh Agent R handoff when present.

### 2. Load the authoritative context

- Read `AGENTS.md` and use its guideline routing to read only the relevant software guidelines.
- Read related Requirement Specifications under `docs/requirements/`.
- Read related ADRs and diagrams when architecture, design, or flow is involved.
- Read `docs/ubiquitous-language.md` when GAM-wide terminology appears.
- Read the relevant role skills when needed to verify that Agent P, Agent T, or Agent D respected its boundaries.

### 3. Review behavior against requirements

Check that:

- implementation satisfies accepted requirements;
- tests protect the intended behavior, boundaries, and failure modes;
- terminology follows the canonical GAM language;
- API contracts and error shapes remain correct;
- persistence and data-integrity behavior are protected;
- defect fixes have an adequate reproduced symptom or a documented test-boundary gap.

Report missing or ambiguous requirements instead of guessing.

### 4. Review guideline compliance

Check relevant concerns such as:

- layer boundaries;
- naming and package organization;
- exception and error-response shape;
- mapper structure;
- persistence rules;
- security and RBAC;
- audit logging;
- API conventions;
- documentation standards;
- ADR coverage for durable architecture decisions.

When sources conflict:

1. identify the conflicting sources;
2. state which source currently governs the affected concern;
3. explain the impact;
4. report the durable artifact that requires correction.

### 5. Review verification evidence

- Identify focused commands that should have been run.
- Identify when broad verification is required.
- Distinguish observed results from claims.
- Report tests that were not run, could not run, or failed for unrelated reasons.
- Do not rerun commands solely to replace missing role work when the review task is intended to evaluate supplied evidence, unless direct verification is part of the requested review scope.

### 7. Route the next action

After reporting findings, use `$gam-agent-workflow` reference `review-routing.md`.

- Missing coverage, a misleading test seam, or an unprotected defect: use `$gam-handoff` to produce a Review Return to Agent T handoff.
- A production issue already exposed by adequate coverage or otherwise unambiguous from accepted sources: use `$gam-handoff` to produce a Review Return to Agent D handoff.
- A Requirement Specification, domain-model, scope, or planning gap: report it as a blocking finding and produce no automatic handoff.
- No actionable findings: produce no handoff.

After writing a handoff, stop. Agent R does not become Agent T or Agent D.

## Boundaries

- Do not implement fixes inside the review session.
- Do not silently assume Agent P, Agent T, or Agent D responsibilities.
- Do not invent missing requirements.
- Do not treat handoffs as review findings or source-of-truth artifacts.
- Do not route an uncovered defect directly to Agent D.
