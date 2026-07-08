# Prompting the agent workflow

Use this guide when starting fresh agent sessions for the workflow in `docs/documentation-guidelines/agent-workflow.md`.

## Core rule

Start a fresh chat when entering a new role. The first prompt should tell the agent:

1. Which workflow role it has.
2. Which skill to use, when a matching skill exists.
3. Which feature, refactor, requirement files, or changed files define the scope.
4. What the agent must produce.
5. Where the agent must stop.

Do not rely only on phrases like "help me with this feature." Make the role and boundary explicit.

## Agent P: planning

Use Agent P before writing tests or implementation.

```text
You are Agent P. Use $gam-planning.

Context:
<feature or refactor context>
```

Agent P should produce planning documentation and a handoff summary for Agent T.

## Agent T: test design

Use Agent T after Agent P has produced accepted or draft requirements that are clear enough to test.

```text
You are Agent T. Use $gam-test-design.

Context:
<feature name and requirement files>
```

Agent T should not implement production code.

## Agent D: implementation

Use Agent D after tests exist and the intended behavior is documented.

```text
You are Agent D. Use $gam-implementation.

Context:
<feature name, requirement files, and test files>
```

Agent D should report requirement gaps instead of guessing business rules.

## Agent T and Agent D alternation

After Agent P finishes, keep Agent T and Agent D in separate open chat sessions for the test/implementation loop.

1. Start Agent T in a fresh chat to write functional tests from the requirements.
2. Start Agent D in a separate fresh chat to implement production code against Agent T's failing tests.
3. Return to the same Agent T chat to add structural and integration tests after Agent D's implementation passes the functional tests.
4. Return to the same Agent D chat to fix bugs exposed by Agent T's expanded suite.
5. Repeat the Agent T and Agent D handoff until the agreed verification scope is satisfied.

When switching between Agent T and Agent D, reference the relevant handoff data: changed files, test files, test output, requirement IDs, and any unresolved questions. Do not merge the roles into one chat.

## Agent R: review

Use Agent R after implementation and tests are ready for review.

```text
You are Agent R. Use $gam-review.

Context:
<feature name, requirement files, changed files, and test results>
```

Agent R should lead with findings and keep summaries secondary.
