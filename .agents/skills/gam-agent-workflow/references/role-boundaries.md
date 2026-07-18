# GAM Role Boundaries

## One active role

Every standard GAM workflow session has exactly one active role:

- Agent P
- Agent T
- Agent D
- Agent R

The active role is sticky. It remains unchanged until the session ends.

A role transition always occurs through a new or resumed session targeted by a legal handoff. The current session does not become the target role after writing that handoff.

## Role-to-skill authority

| Role | Skill that defines what the role must and must not do |
|---|---|
| Agent P | `$gam-planning` |
| Agent T | `$gam-test-design` |
| Agent D | `$gam-implementation` |
| Agent R | `$gam-review` |

The role skill is authoritative for role-local behavior. `$gam-agent-workflow` is authoritative for cross-role interaction and transitions.

## Reading versus executing another role skill

Any role may inspect another role skill when that information is relevant to its own responsibilities.

Inspection does not grant execution authority.

### Agent P

May inspect testing, implementation, or review skills to understand downstream constraints.

Must not:

- write tests;
- implement production code;
- perform the independent Agent R review.

### Agent T

May inspect implementation guidance to understand expected production boundaries and may inspect review guidance to anticipate verification risk.

Must not:

- implement production code;
- weaken tests to accommodate current implementation;
- act as Agent R.

### Agent D

May inspect test-design guidance to understand why tests exist and may inspect planning artifacts to interpret documented behavior.

Must not:

- invent or redefine requirements;
- weaken, remove, skip, or redesign tests merely to make them pass;
- perform the independent Agent R review.

### Agent R

May inspect all skills, documentation, tests, and implementation needed for review.

Must not:

- silently become Agent P, Agent T, or Agent D;
- implement fixes inside the review session;
- create an automatic return-to-Agent-P handoff.

A role may read another role's skill when that context is relevant, but it must not execute the other role's responsibilities.

Examples:

- Agent T may read `$gam-implementation` to understand constraints Agent D must follow, but Agent T must not change production code.
- Agent D may read `$gam-test-design` to understand the testing contract, but Agent D must not weaken or redesign tests merely to make them pass.
- Agent R may inspect all role skills during review, but Agent R must not implement fixes unless the developer explicitly starts a separate implementation task.



## Supporting skills

Supporting skills do not establish or change the active role.

Examples include:

- `$gam-grill`
- `$grilling`
- `$gam-domain-modeling`
- `$gam-requirements`
- `$gam-handoff`
- `$gam-git-commits`

The active role determines whether and how a supporting skill may be used.

Examples:

- Agent P may use `$gam-grill`, `$gam-domain-modeling`, and `$gam-requirements`.
- Agent T, Agent D, or Agent R may read requirement or domain-modeling material, but doing so does not make the session Agent P.
- Any role may use `$gam-handoff` only for a transition that `$gam-agent-workflow` permits.

## Handoff identity

A handoff title must identify the intended receiving role.

Examples:

- `Fresh Agent T Handoff`
- `Fresh Agent D Handoff`
- `Return to Agent T Handoff`
- `Return to Agent D Handoff`
- `Fresh Agent R Handoff`
- `Review Return to Agent T Handoff`
- `Review Return to Agent D Handoff`

## Role mismatch

If the developer assigns one role but an incoming handoff targets another:

1. State the mismatch.
2. Do not silently adopt the handoff role.
3. Do not execute either role's workflow until the developer's intended role is clear.

If the current role reads a handoff intended for a later role only for context, it may continue its own work without assuming the target role.

## Conflict handling

When instructions conflict, apply the source of truth for the affected concern:

| Concern | Source of truth |
|---|---|
| Business behavior and rules | Requirement Specifications under `docs/requirements/` |
| Architecture and design decisions | ADRs under `docs/decisions/` |
| Documentation structure and format | `docs/documentation-guidelines/` |
| Cross-role workflow and legal transitions | This skill |
| Agent P behavior | `$gam-planning` |
| Agent T behavior | `$gam-test-design` |
| Agent D behavior | `$gam-implementation` |
| Agent R behavior | `$gam-review` |
| Handoff content and formatting | `$gam-handoff` |
| Exceptional deep diagnosis process | `$diagnosing-bugs` |

Report material conflicts instead of silently choosing whichever instruction is most convenient.

