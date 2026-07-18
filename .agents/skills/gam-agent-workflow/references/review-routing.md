# Agent R Review Routing

## Purpose

Agent R decides what should happen after independent review without assuming another role or creating an ambiguous “return to loop” handoff.

Agent R may produce:

- a Review Return to Agent T handoff;
- a Review Return to Agent D handoff;
- no handoff.

## Route to Agent T

Produce a Review Return to Agent T handoff when the review finds:

- missing functional, structural, integration, API, security, persistence, or regression coverage;
- a defect that is not yet protected by an adequate failing test;
- a test that gives false confidence because it uses the wrong seam;
- a regression claim whose original symptom was never demonstrated;
- a verification gap that requires test-design judgment rather than only rerunning an established command.

Agent T must establish or correct the test signal before Agent D changes production behavior.

## Route to Agent D

Produce a Review Return to Agent D handoff only when:

- an existing adequate test already exposes the production defect; or
- the review identifies a production or documentation implementation issue whose expected correction is already unambiguous from accepted requirements, ADRs, and existing coverage.

The handoff must reference the existing evidence that makes the production issue actionable. Agent R must not invent a new expected behavior.

## Produce no handoff

Produce no handoff when:

- no actionable findings remain;
- the review finds a Requirement Specification, domain-model, scope, or planning contradiction that Agent T or Agent D cannot correctly resolve;
- the developer must decide whether to begin a separate planning session;
- the review is intentionally informational and no immediate continuation is requested.

For requirement or planning gaps, report the issue as a blocking finding with:

1. the conflicting or missing sources;
2. why Agent T or Agent D cannot safely proceed;
3. the decision or clarification needed.

## Mixed findings

When findings would independently route to both Agent T and Agent D, prefer Agent T first if any defect lacks adequate failing coverage.

Agent D should receive work only after the required behavioral signal exists.

If all production defects are already adequately covered and separate coverage improvements are non-blocking, Agent R may route the production defects to Agent D and list the non-blocking coverage concerns in the review findings. Avoid creating two competing immediate handoffs.

## Handoff mechanics

After selecting the route through this file, use `$gam-handoff` and read only `gam-handoff/references/review-return.md` for the output format.

The review session remains Agent R after writing the handoff and stops.
