---
name: gam-handoff
description: Compact the current conversation into a chat-ready handoff for another agent chat or role transition.
---

Write a handoff directly in the chat response so the developer can copy and paste it into the intended next agent chat.

Do not save the handoff to the repository, the OS temporary directory, or any other file unless the developer explicitly asks for a saved artifact.

Select the handoff type before writing:

1. **Fresh Agent T handoff**: use when starting a new Agent T chat from another role's current context, usually after Agent P creates requirements.
2. **Fresh Agent D handoff**: use when starting a new Agent D chat from another role's current context, usually after Agent T creates red tests.
3. **Return to Agent T handoff**: use inside the Agent T / Agent D alternation when the developer will paste the note back into the same Agent T chat after Agent D implementation.
4. **Return to Agent D handoff**: use inside the Agent T / Agent D alternation when the developer will paste the note back into the same Agent D chat after Agent T expands tests.
5. **Fresh Agent R handoff**: use after the Agent T / Agent D alternation is complete and the developer wants review in a new Agent R chat.

For fresh-agent handoffs, make the handoff self-identifying. Include only the sections that are useful for the task, usually:

- Suggested Skills
- Next Role
- Source Of Truth
- Current Status
- Verification State
- Open Questions / Risks

For return-to-same-chat handoffs, assume the receiving Agent T or Agent D already knows the earlier requirements, role boundary, and source-of-truth context from that chat. Omit boilerplate sections such as Suggested Skills, Next Role, and Source Of Truth unless something new or surprising must be loaded. Prefer a shorter shape:

- Current Status
- Verification State, if it changed or matters for the next step
- Important Scope Note, if there is a boundary or decision to preserve
- Open Questions / Risks, if unresolved

Keep every handoff compact. Do not restate rules that the next agent will already load from `AGENTS.md`, the suggested skill, or project documentation. Include only session-specific facts, deviations, decisions, unresolved issues, changed files, verification evidence, and durable artifact references.

Do not duplicate content already captured in other artifacts such as Requirement Specifications, ADRs, issues, commits, diffs, or test output. Reference them by path, URL, commit, or command instead.

Redact any sensitive information, such as API keys, passwords, or personally identifiable information.

If the developer passed arguments, treat them as a description of what the next session will focus on and tailor the doc accordingly.
