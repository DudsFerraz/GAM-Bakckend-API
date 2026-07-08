# Agent implementation workflow

The standard agent-assisted implementation process is:

```mermaid
flowchart TD
    A["Client request / task"] --> P["Agent P: planning session"]
    P --> B["Evaluate requirements"]
    B --> C["Grilling + developer interpretation"]
    C --> M["Domain modeling + docs decisions"]
    M --> D["Requirement Specification with stable IDs"]

    D --> T1["Agent T: write functional tests from requirements"]
    T1 --> RED["Functional tests fail red"]

    RED --> D1["Agent D: implement code against failing tests"]
    D1 --> G1["Functional tests pass"]

    G1 --> T2["Agent T: add structural and integration tests"]
    T2 --> BUGS{"New failures?"}

    BUGS -- Yes --> D2["Agent D: fix exposed bugs"]
    D2 --> V["Run focused and full verification"]

    BUGS -- No --> V

    V --> R["Agent R: review requirements, tests, code, docs"]
    R --> CONFLICT{"Conflicts or gaps?"}

    CONFLICT -- Yes --> C2["Report conflict, winning source, rationale"]
    C2 --> D

    CONFLICT -- No --> DONE["Ready for PR / commit"]
```

This workflow may be compressed for small or low-risk changes, but requirement clarity, test derivation, implementation, verification, and review must remain distinct concerns.

Agent P is responsible for the planning phase: evaluate the request, grill the developer until shared understanding is reached, sharpen domain terminology, identify ADR-worthy decisions, create diagrams when useful, and produce Requirement Specifications with stable IDs. Agent P stops before tests or implementation; Agent T starts from the resulting requirements in a fresh context.

---
