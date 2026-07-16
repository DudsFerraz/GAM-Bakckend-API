# Preparing an Agent Task

Use this page when asking an agent to plan, design tests, implement, or review a GAM change. Keep each role in a separate chat and give the task a precise boundary.

## Include in every task brief

1. The requested role: planning, test design, implementation, or review.
2. The relevant feature and Requirement Specification files.
3. The files or tests in scope.
4. The expected result.
5. The stopping point.

Example:

```text
Role: implementation
Feature: <feature name>
Requirements: <paths>
In scope: <paths>
Expected result: <behavior and tests>
Stop after: <verification command and report>
```

Do not send only “implement this feature.” State the behavior, source documents, boundaries, and verification expected.

## Recommended order

1. Planning: clarify the domain, requirements, decisions, risks, and open questions.
2. Test design: derive tests from the accepted requirements.
3. Implementation: change production code against the tests and requirements.
4. Review: inspect requirements, tests, code, documentation, and verification results.

Keep test design and implementation in separate chats. After implementation, return to test design for structural and integration coverage, then return to implementation for exposed defects.

## Handoffs

Include the feature, requirement files, changed files, test results, known risks, and the next role. Use [`agent-workflow.md`](../documentation-guidelines/agent-workflow.md) for the project workflow and `.agents/skills` for the applicable GAM skill.

Do not ask an agent to guess missing business rules. Record the gap and resolve it in the owning requirement before proceeding.
