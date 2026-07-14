# Member Lifecycle and Membership Solicitation

This diagram supports the Member Records and Lifecycle and Membership Solicitations Requirement Specifications. Written requirements remain authoritative.

```mermaid
flowchart TD
    Eligible["Eligible Account without a Member"]
    Submit["Submit membership solicitation"]
    Pending["PENDING solicitation"]
    Reject["Coordinator rejects with reason"]
    Rejected["REJECTED solicitation; no Member"]
    Approve["Coordinator approves with reason"]
    Direct["Coordinator directly registers with reason"]
    Active["ACTIVE Member; MEMBER assigned; VISITOR absent"]
    Deactivate["Deactivate with reason"]
    Inactive["INACTIVE Member; VISITOR assigned; MEMBER absent"]
    Reactivate["Reactivate with reason"]
    Blocked["409 Conflict"]

    Eligible --> Submit --> Pending
    Pending --> Reject --> Rejected
    Rejected -->|"New immutable solicitation"| Submit
    Pending --> Approve --> Active
    Eligible --> Direct --> Active
    Pending -->|"Direct registration attempted"| Blocked
    Active --> Deactivate --> Inactive
    Inactive --> Reactivate --> Active
    Active -->|"Activate again"| Blocked
    Inactive -->|"Deactivate again"| Blocked
```

## Related requirements

* [Member Records and Lifecycle](../requirements/members/member-records-and-lifecycle.md)
* [Membership Solicitations](../requirements/members/membership-solicitations.md)

## Related ADRs

* [ADR-0004: Make Member lifecycle own MEMBER and VISITOR roles](../decisions/0004-member-lifecycle-owns-member-and-visitor-roles.md)
