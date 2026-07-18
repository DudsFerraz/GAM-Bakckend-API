# Return to Agent T Handoff

## Use when

Agent D has completed an implementation pass and the existing Agent T context should resume to evaluate or add expanded coverage.

This is a delta handoff. Do not repeat planning context that Agent T already has.

## Required Content

- `Current Status`
- `Changes`, when production or documentation files changed in a way Agent T needs to inspect, otherwise this section remains empty
- `Verification`

## Conditional content

Include only when relevant:

- `Context`, when a new or surprising durable artifact must be loaded
- `Scope`
- `Risks`
- `Open Questions`
- `Decision`

## Template

```md
# Return to Agent T Handoff: <feature or refactor>

## Current Status
- <implementation status and the reason Agent T should resume>

## Changes
- `<production or documentation file>` — <change relevant to test design>

## Verification
- `<focused command>` — <observed result>
- `<broad command, when required>` — <observed result or unrelated blocker>
```

Do not tell Agent T which tests it must add unless the requirement or an existing documented risk already makes that necessary.
