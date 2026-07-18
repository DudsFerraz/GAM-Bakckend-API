# Return to Agent D Handoff

## Use when

Agent T's expanded test pass exposes a production issue and the existing Agent D context should resume.

This is a delta handoff. Do not repeat planning or initial implementation context already available to Agent D.

## Required content

- `Current Status`
- `Changes`
- `Verification`

## Conditional content

Include only when relevant:

- `Context`
- `Scope`
- `Risks`
- `Open Questions`
- `Decision`

## Content guidance

`Changes` identifies the newly added or changed tests and the behavior they expose.

`Verification` gives the exact command and meaningful failure signal.

If the failure may reflect a requirement gap rather than a production defect, state that risk explicitly. Do not instruct Agent D to guess the intended rule.

## Template

```md
# Return to Agent D Handoff: <feature or refactor>

## Current Status
- <expanded coverage exposes the remaining production issue>

## Changes
- `<test file>` — <new coverage category or behavior>

## Verification
- `<command>` — <failure count and meaningful failure signal>

## Risks
- <only when the failure may indicate a requirement or test-boundary gap>
```
