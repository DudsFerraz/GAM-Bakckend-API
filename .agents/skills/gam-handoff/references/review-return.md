# Review Return Handoffs

## Select the target first

Read `$gam-agent-workflow` reference `review-routing.md` before using this file.

The handoff title must explicitly target Agent T or Agent D.

---

## Review Return to Agent T

### Use when

Agent R finds missing or misleading coverage, or a defect that is not yet protected by an adequate failing test.

### Required content

- `Current Status`
- `Review Findings`
- `Changes`, when specific test or production files must be inspected
- `Verification`

### Conditional content

- `Context`
- `Scope`
- `Risks`
- `Open Questions`
- `Decision`

### Template

```md
# Review Return to Agent T Handoff: <feature or refactor>

## Current Status
- <why the reviewed work is returning to test design>

## Review Findings
- `<file:line or artifact>` — <coverage gap, incorrect seam, or unprotected defect>

## Changes
- `<relevant file>` — <why Agent T needs to inspect it>

## Verification
- `<command>` — <observed result or missing verification signal>

## Risks
- <only when the finding may expose a requirement or test-boundary gap>
```

Agent T determines the correct coverage. Do not prescribe production changes.

---

## Review Return to Agent D

### Use when

Agent R finds a production issue that is already exposed by an adequate test or is otherwise unambiguous from accepted requirements, ADRs, and existing evidence.

### Required content

- `Current Status`
- `Review Findings`
- `Changes`
- `Verification`

### Conditional content

- `Context`
- `Scope`
- `Risks`
- `Open Questions`
- `Decision`

### Template

```md
# Review Return to Agent D Handoff: <feature or refactor>

## Current Status
- <why the reviewed work is returning to implementation>

## Review Findings
- `<file:line or artifact>` — <production issue and the accepted source that defines the expected behavior>

## Changes
- `<test or production file>` — <evidence Agent D must inspect>

## Verification
- `<command>` — <existing failing signal or observed verification result>

## Risks
- <only when a remaining ambiguity could block a safe fix>
```

Do not route an uncovered defect directly to Agent D.
