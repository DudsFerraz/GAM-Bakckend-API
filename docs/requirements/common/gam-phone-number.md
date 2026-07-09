# Requirement: GamPhoneNumber

## Status
Accepted

## Context
GAM stores phone numbers for people such as Members and Oratorianos. Phone numbers may be entered in local Brazilian form or explicit international form, but persisted values need one canonical representation.

The `GamPhoneNumber` primitive defines the valid shape and canonical representation of a present phone number. Whether a phone number is required, optional, unique, or contactable belongs to the owning feature's Requirement Specification.

## Ubiquitous Language
- `E.164`: The canonical international phone number format beginning with `+` and a country code.
- `default region`: The country used to interpret a phone number when no explicit international prefix is provided.

## Functional requirements

### REQ-GAM-PHONE-001: Required present value
The `GamPhoneNumber` primitive shall reject null and blank values when a `GamPhoneNumber` value is created.

Rationale:
A present phone-number primitive must represent an actual phone number value, not absence.

Valid examples:
- `+5519998877665`
- `(19) 99887-7665`

Invalid examples:
- `null`
- `""`
- `"   "`

---

### REQ-GAM-PHONE-002: Brazil default region
The `GamPhoneNumber` primitive shall use Brazil (`BR`) as the default region when parsing input that does not include an explicit international country code.

Rationale:
GAM's operating context is Brazil, so ambiguous local phone input should be interpreted as Brazilian by default.

Valid examples:
- `(19) 99887-7665` is interpreted as a Brazilian phone number.
- `19998877665` is interpreted as a Brazilian phone number.

Invalid examples:
- Interpreting `19998877665` with a non-Brazilian default region.

---

### REQ-GAM-PHONE-003: Explicit international numbers
The `GamPhoneNumber` primitive shall accept non-Brazilian phone numbers when they are provided with an explicit international country code and are valid dialable numbers.

Rationale:
GAM is Brazil-first, but legitimate contacts may have international phone numbers.

Valid examples:
- `+5519998877665`
- A valid international number beginning with a non-Brazilian country code.

Invalid examples:
- A non-Brazilian local-format number without an explicit country code.
- A number with an explicit country code that is not valid or dialable.

---

### REQ-GAM-PHONE-004: Dialable-number validation
The `GamPhoneNumber` primitive shall reject unparseable values and parsed values that are not valid dialable phone numbers.

Rationale:
The primitive should store real phone numbers instead of arbitrary strings.

Valid examples:
- `+5519998877665`
- `(19) 99887-7665`

Invalid examples:
- `abc`
- `+5500000000000`

---

### REQ-GAM-PHONE-005: Canonical representation
The `GamPhoneNumber` primitive shall store and expose its canonical value in E.164 format.

The `GamPhoneNumber` primitive may expose national display format, country code, and national number as derived metadata.

Rationale:
E.164 gives GAM one stable representation for persistence, comparison, and API output while still allowing user-friendly display.

Valid examples:
- `+5519998877665` is stored as `+5519998877665`.
- `(19) 99887-7665` is stored as `+5519998877665`.

Invalid examples:
- Storing `(19) 99887-7665` as the canonical value.
- Storing `19998877665` without a country code as the canonical value.

## Acceptance scenarios

```gherkin
Scenario: Normalize Brazilian local input
  Given the raw phone number is "(19) 99887-7665"
  When a GamPhoneNumber value is created
  Then the creation succeeds
  And the canonical value is "+5519998877665"

Scenario: Accept explicit international input
  Given the raw phone number includes an explicit international country code
  And the phone number is valid and dialable
  When a GamPhoneNumber value is created
  Then the creation succeeds

Scenario: Reject malformed phone input
  Given the raw phone number is "abc"
  When a GamPhoneNumber value is created
  Then the creation fails
```

## Open questions

* None.

## Out of scope

* Whether a feature requires or allows a phone number.
* Phone-number uniqueness.
* SMS or WhatsApp verification.
* Contactability checks.
* Carrier, line type, or ownership checks.

## Related ADRs

* None.

## Related videos

* None.
