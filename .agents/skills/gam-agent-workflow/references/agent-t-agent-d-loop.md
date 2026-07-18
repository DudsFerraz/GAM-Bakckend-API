# Agent T / Agent D Loop

## Purpose

The Agent T / Agent D loop separates test design from production implementation while allowing both roles to iterate on the same documented behavior.

Agent T owns test design and test implementation.

Agent D owns production implementation.

Neither role absorbs the other's responsibilities.

## Stage 1: Functional red signal

Agent T:

1. Reads the accepted Requirement Specification and relevant ADRs or diagrams.
2. Designs functional coverage through the narrowest appropriate public seam.
3. Writes the functional tests.
4. Runs them and confirms that they fail for the expected missing behavior rather than for an unrelated environment or test defect.
5. Produces a Fresh Agent D handoff.

The initial handoff must identify the test files and the observed red signal. It must not prescribe implementation details unless an accepted ADR already requires them.

## Stage 2: Initial implementation

Agent D:

1. Reads the documented requirements, the failing tests, and their failure output.
2. Implements the minimum production behavior needed to satisfy the documented contract.
3. Runs focused verification.
4. Does not weaken or redesign Agent T's tests merely to obtain green.
5. Produces a Return to Agent T handoff when the functional tests pass.

The return handoff identifies production changes and observed verification. It does not tell Agent T which specific tests to write next.

## Stage 3: Expanded coverage

Agent T resumes in the same testing context and evaluates whether the feature risk calls for additional:

- structural tests;
- integration tests;
- API tests;
- security tests;
- persistence tests;
- boundary and failure-mode coverage.

If expanded coverage exposes a production defect, Agent T produces a Return to Agent D handoff containing the new tests and their meaningful failure signal.

If no production issue remains and the agreed verification is complete, Agent T may produce a Fresh Agent R handoff.

## Stage 4: Fixing exposed production issues

Agent D resumes in the same implementation context:

1. Reads the newly added tests and their failure output.
2. Fixes the production behavior without weakening the tests.
3. Runs focused and required broad verification.
4. Returns to Agent T when more test-design work is still expected.
5. Produces a Fresh Agent R handoff when the agreed loop and verification are complete and no further Agent T pass is needed.

## Completion criteria

The Agent T / Agent D loop is ready for Agent R only when:

- the relevant documented requirements have test coverage appropriate to their risk;
- the agreed focused tests pass;
- required broad verification has passed or any unrelated blocker is explicitly documented;
- no known production defect remains hidden by weakened, skipped, or misleading tests;
- unresolved requirement ambiguity has been reported rather than guessed.

## Blockers

### Requirement ambiguity

Neither Agent T nor Agent D may invent missing business rules.

When a requirement gap prevents correct testing or implementation, report the gap and stop the affected work. The developer decides whether to begin a separate Agent P planning session.

### Incorrect test

Agent D may report that a test conflicts with an accepted requirement or tests the wrong seam. Agent D must not silently rewrite it.

Agent T evaluates test correctness in its own session.

### Missing regression seam

When a documented defect cannot be protected at a correct test seam, Agent T records the test-boundary gap rather than writing a misleading test.

### Unrelated verification failure

A handoff must distinguish the expected signal for the current work from unrelated test, environment, or infrastructure failures.
