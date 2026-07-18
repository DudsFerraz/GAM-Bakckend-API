---
name: gam-handoff
description: Write a compact chat-ready handoff for a legal GAM role transition. Use after $gam-agent-workflow identifies the target role and handoff type.
---

# GAM Handoff

## Overview

A handoff is an ephemeral delta packet for moving work between agent chats or resuming an Agent T / Agent D loop.

It is not:

- project documentation;
- a replacement for Requirement Specifications, ADRs, diagrams, tests, diffs, commits, or verification output;
- a transcript;
- permission for the current session to assume the target role.

`$gam-agent-workflow` is authoritative for whether a transition is legal.

This skill is authoritative for how that transition is written.

## Workflow

1. Use `$gam-agent-workflow` to identify the legal target role and handoff type.
2. Select the matching reference file from the table below.
3. Read only that type-specific reference.
4. Write the handoff directly in the chat response so the developer can copy and paste it into the intended session.
5. Stop. The current session remains in its existing role.

## Handoff types

| Source role | Target role | Handoff type | Read |
|---|---|---|---|
| Agent P | Agent T | Fresh Agent T | `references/fresh-agent-t.md` |
| Agent T | Agent D | Fresh Agent D | `references/fresh-agent-d.md` |
| Agent D | Agent T | Return to Agent T | `references/return-agent-t.md` |
| Agent T | Agent D | Return to Agent D | `references/return-agent-d.md` |
| Agent T or Agent D | Agent R | Fresh Agent R | `references/fresh-agent-r.md` |
| Agent R | Agent T or Agent D | Review Return | `references/review-return.md` |

## Common content rules

Use only sections that contain information the receiving agent needs.

Prefer references over repetition:

- Reference Requirement Specifications instead of summarizing all requirements.
- Reference ADRs instead of repeating their rationale.
- Reference test files instead of reproducing test code.
- Reference changed production files or a diff instead of restating implementation.
- Include exact verification commands and observed results when they affect the next action.
- Distinguish expected red signals from unrelated failures.
- Include only session-specific risks, scope constraints, and open questions.

Omit empty sections.

When a durable decision was made, first record it in the correct Requirement Specification, ADR, diagram, or ubiquitous-language artifact. Add an optional `Decision` section only when the receiving agent would otherwise miss a necessary transition detail. Reference the durable artifact and state only its one-line impact.

## Role identity

The title must name the target role. The receiving agent should be able to identify its intended role immediately.

The handoff must not reteach that role's complete workflow. The receiving session reads its authoritative role skill.

Do not repeat:

- role responsibilities already defined by the target role skill;
- general prohibitions already defined by `$gam-agent-workflow`;
- instructions already supplied by `AGENTS.md` or project guidelines.

## Fresh versus return handoffs

A fresh handoff starts a new role chat and normally includes enough context to locate the durable artifacts and current repository state.

A return handoff resumes an existing Agent T or Agent D context and should contain only the delta since that context last acted.