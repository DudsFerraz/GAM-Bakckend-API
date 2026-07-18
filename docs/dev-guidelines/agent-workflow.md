# GAM agent workflow overview

This document is a human-readable index of the GAM agent workflow.

It is not the normative source for agent behavior.

The authoritative workflow instructions live under `.agents/skills/`.

## Authority map

| Concern | Authoritative source |
|---|---|
| Cross-role process, sticky role identity, legal transitions, Agent T / Agent D loop, and review routing | [`.agents/skills/gam-agent-workflow/SKILL.md`](../../.agents/skills/gam-agent-workflow/SKILL.md) |
| Agent P planning behavior | [`.agents/skills/gam-planning/SKILL.md`](../../.agents/skills/gam-planning/SKILL.md) |
| Agent T test-design behavior | [`.agents/skills/gam-test-design/SKILL.md`](../../.agents/skills/gam-test-design/SKILL.md) |
| Agent D implementation behavior | [`.agents/skills/gam-implementation/SKILL.md`](../../.agents/skills/gam-implementation/SKILL.md) |
| Agent R review behavior | [`.agents/skills/gam-review/SKILL.md`](../../.agents/skills/gam-review/SKILL.md) |
| Handoff format and type-specific content | [`.agents/skills/gam-handoff/SKILL.md`](../../.agents/skills/gam-handoff/SKILL.md) |
| Exceptional deep diagnosis process | [`.agents/skills/diagnosing-bugs/SKILL.md`](../../.agents/skills/diagnosing-bugs/SKILL.md) |

Requirement Specifications, ADRs, ubiquitous-language material, diagrams, and documentation guidelines remain the durable sources for product behavior and project decisions.

## Standard Workflow

```mermaid
flowchart TD
    A["Client request / task"] --> P["Agent P: planning"]
    P --> RS["Requirement Specifications, ADRs, diagrams, open questions"]
    RS --> HT["Fresh Agent T handoff"]

    HT --> T1["Agent T: functional tests"]
    T1 --> RED["Expected red signal"]
    RED --> HD["Fresh Agent D handoff"]

    HD --> D1["Agent D: initial implementation"]
    D1 --> GREEN["Functional tests pass"]
    GREEN --> RT["Return to Agent T handoff"]

    RT --> T2["Agent T: expanded test coverage"]
    T2 --> BUGS{"Production gaps exposed?"}

    BUGS -- Yes --> RD["Return to Agent D"]
    RD --> D2["Agent D: fix exposed production issues"]
    D2 --> LOOP{"More Agent T work needed?"}
    LOOP -- Yes --> RT
    LOOP -- No --> HR["Fresh Agent R handoff"]

    BUGS -- No --> HR
    HR --> R["Agent R: independent review"]
    R --> FINDINGS{"Actionable findings?"}

    FINDINGS -- "Missing coverage or unprotected defect" --> RRT["Review Return to Agent T"]
    FINDINGS -- "Adequately tested production defect" --> RRD["Review Return to Agent D"]
    FINDINGS -- "Requirement or planning gap" --> BLOCK["Report blocking finding; no automatic handoff"]
    FINDINGS -- No --> DONE["Ready for commit / PR"]

    RRT --> T2
    RRD --> D2
```

The diagram is illustrative only. Read `$gam-agent-workflow` for the authoritative transition conditions and role boundaries.

## Role model

A standard workflow session has one active role:

- Agent P plans.
- Agent T designs and implements tests.
- Agent D implements production behavior.
- Agent R independently reviews the result.

The active role remains unchanged during the session.

An agent may read another role's skill for context, but that does not authorize it to perform the other role's work.

## Handoffs

Handoffs are ephemeral context-transfer aids.

They identify the receiving role and reference durable artifacts such as Requirement Specifications, ADRs, diagrams, tests, changed files, diffs, and verification output.

They are not project documentation and do not replace the authoritative role or workflow skills.

## Diagnosis mode

Diagnosis mode is separate from the standard Agent P / Agent T / Agent D / Agent R workflow and requires explicit developer activation.

It establishes reproduction evidence and cause only. Durable regression coverage returns to Agent T, and the production fix returns to Agent D.
