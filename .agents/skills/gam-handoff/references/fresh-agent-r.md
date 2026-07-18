# Fresh Agent R Handoff

## Use when

The agreed Agent T / Agent D loop and verification are complete and a new Agent R session should independently review the work.

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

`Changes` should reference the relevant changed files, diff, branch, or commit without reproducing their contents.

`Verification` should include focused and broad commands when both matter. State any command that was not run or any unrelated blocker.

## Template

```md
# Fresh Agent R Handoff: <feature or refactor>

## Context
- <one-sentence feature or refactor focus>
- `<Requirement Specification>`
- `<Relevant ADR or diagram, when needed>`

## Current Status
- <implementation and test-loop status>

## Changes
- `<changed files, diff, branch, or commit reference>`

## Verification
- `<focused command>` — <observed result>
- `<full verification command>` — <observed result or documented blocker>
```

Omit every conditional section that has no useful content.
