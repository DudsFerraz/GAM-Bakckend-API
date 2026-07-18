# Fresh Agent T Handoff

## Use when

Agent P has completed planning, the relevant requirements are ready for test design, and a new Agent T session should begin.

Do not use this handoff while a planning blocker still prevents correct test derivation.

## Required content

- `Context`

Include only the brief feature or refactor focus and references to the durable artifacts Agent T must use.

## Conditional content

Include these sections only when they affect test design:

- `Current Status`
- `Changes`
- `Verification`
- `Scope`
- `Risks`
- `Open Questions`
- `Decision`

Open questions included here must be non-blocking. A blocking requirement ambiguity means planning is not ready for this handoff.

## Template

```md
# Fresh Agent T Handoff: <feature or refactor>

## Context
- <one-sentence feature or refactor focus>
- `<Requirement Specification>`
- `<Relevant ADR or diagram, when needed>`

## Current Status
- <non-obvious planning or discovery fact that is not already captured in the referenced artifacts>

## Risks
- <session-specific risk that materially affects test design>
```

Omit every conditional section that has no useful content.
