# Fresh Agent D Handoff

## Use when

Agent T has written the initial functional tests, confirmed the expected red signal, and a new Agent D session should implement the documented behavior.

## Required content

- `Context`
- `Current Status`
- `Changes`
- `Verification`

## Conditional content

Include only when relevant:

- `Scope`
- `Risks`
- `Open Questions`
- `Decision`

## Content guidance

`Changes` identifies the test files Agent D must satisfy and briefly states what each protects.

`Verification` gives the exact command and observed result. Distinguish the expected failures caused by missing production behavior from unrelated failures.

Do not prescribe an implementation approach unless an accepted ADR already requires it.

## Template

```md
# Fresh Agent D Handoff: <feature or refactor>

## Context
- <one-sentence feature or refactor focus>
- `<Requirement Specification>`
- `<Relevant ADR, when needed>`

## Current Status
- <functional tests exist; production implementation status>

## Changes
- `<test file>` — <coverage or purpose relevant to implementation>

## Verification
- `<command>` — <expected red signal and any unrelated failures>
```

Omit every conditional section that has no useful content.
