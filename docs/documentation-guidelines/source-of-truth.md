# Source of truth priority

When sources conflict the user must be notified, use this priority if the conflicts are not resolved:

```text
1. Accepted requirements
2. Global glossary for canonical GAM terminology
3. Accepted ADRs
4. OpenAPI contract
5. Mermaid diagrams
6. Testing documentation
7. Existing implementation
8. Supplemental YouTube videos
```

Existing implementation must not be treated as the source of truth for business rules unless explicitly documented.

The global glossary defines canonical terms and aliases to avoid, but it does not define feature behavior. If a terminology rule and an accepted Requirement Specification appear to conflict, report the conflict and prefer the accepted requirement for behavior while preserving the glossary as the terminology baseline until updated.


