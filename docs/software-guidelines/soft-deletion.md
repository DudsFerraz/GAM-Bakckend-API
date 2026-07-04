# Persistence and Soft Delete Guidelines

## 1. Purpose

**Soft delete is an internal security and safety mechanism, not a user-facing feature.** Its purpose is to protect the system from mistaken deletions, abusive administration, and irreversible data loss. Normal users and administrators interact with the system using domain actions (e.g., *deactivate*, *cancel*), while historical facts are preserved in the database.

---

## 2. Core Architecture Rules

### 2.1. Soft Delete Visibility and Repositories

Soft-deleted rows must be strictly hidden from normal application reads.

* **Mechanism:** Every soft-deletable entity uses `@SQLRestriction` (or the standardized framework equivalent) to automatically filter out deleted rows.
* **Repository API:** Normal application repositories (`BaseRepository`) do **not** expose dangerous operations. Methods like `hardDelete`, `findAllDeleted`, or `restore` are strictly forbidden in application services.
* **Cascading:** Soft delete does not automatically cascade to historical child records unless explicitly defined by a specific domain policy.

### 2.2. Unique Values After Soft Delete

Only active rows reserve unique values.

* **Mechanism:** Tables use partial unique indexes (e.g., `UNIQUE (email) WHERE deleted_at IS NULL`).
* This allows a user to reuse an email address, role name, or event pairing if the previous record was soft-deleted.

### 2.3. Developer Maintenance Tooling

Restoring, hard-deleting, or browsing soft-deleted records is completely inaccessible via the HTTP API. These actions are restricted to developer-controlled, command-line maintenance jobs using the `maintenance` Spring profile.

```powershell
# Example: Inspect deleted rows
mvn spring-boot:run "-Dspring-boot.run.profiles=maintenance" "-Dspring-boot.run.arguments=--maintenance.action=inspect-soft-deleted --maintenance.table=members"

# Example: Restore a row (requires manual resolution if a unique constraint conflicts)
mvn spring-boot:run "-Dspring-boot.run.profiles=maintenance" "-Dspring-boot.run.arguments=--maintenance.action=restore --maintenance.table=members --maintenance.id=<uuid> --maintenance.reason='Restored after developer review'"
```

---

## 3. User-Facing Actions vs. Technical Soft Delete

The UI and application workflows must avoid soft-delete terminology. Instead of "deleting" records, the system exposes intentional domain actions. The underlying technical implementation may execute a soft delete, but the user intent is distinct.

| Technical Action       | Domain Action Exposed to User                                |
|------------------------|--------------------------------------------------------------|
| Soft Delete `Member`   | **Deactivate** member                                        |
| Soft Delete `Event`    | **Cancel** event (or delete if within the correction window) |
| Soft Delete `Presence` | **Remove** mistaken presence                                 |
| Soft Delete `Role`     | **Disable / Remove** custom role                             |

---

## 4. Entity Policy Archetypes

Different entities follow different lifecycle rules regarding deletion.

### 4.1. People and Identity (`Account`, `Member`, `Oratoriano`)

**Rule: Deactivate, do not delete.**

* These entities are audit actors and participate in historical facts (e.g., event presences). Deleting them risks rewriting history.
* Use deactivation/disabling to express their current inactive state.
* *Exception:* Soft deletion is only permitted internally for immediate corrections (e.g., a member was created by mistake and has zero historical dependencies).

### 4.2. Events (`Event`, `Missa`, `Oratorio`)

**Rule: Cancel, do not delete (outside the correction window).**

* If an event is real and part of history, it must be **cancelled**, not deleted.
* A soft-delete action is only allowed if it falls within a strict, configurable correction window (e.g., 15 minutes after creation), the event has not started, and it has zero presences or operational data attached.

### 4.3. RBAC & Configuration (`Role`, `Permission`, `Location`)

**Rule: Protect system data; allow removal of unused custom data.**

* Seeded baseline roles and permissions contain a `systemManaged` marker and can **never** be deleted.
* User-created roles and permissions can be soft-deleted only if no active assignments depend on them.
* Locations can only be soft-deleted if they are not referenced by any historical events.

### 4.4. Security Artifacts (`RefreshToken`)

**Rule: Hard delete always.**

* Refresh tokens are security/session artifacts, not business history. They do not possess soft-delete columns. They are hard-deleted upon logout, rotation, or expiration.

### 4.5. Join Tables and Assignments

**Rule: Lifecycle is tied to the aggregate.**

* Simple join tables without entities (e.g., `oratorio_lanche`) do not use soft delete. Their lifecycle is managed entirely by the owning aggregate.
* Rich assignment entities (e.g., `AccountRole`, `RolePermission`, `Presence`) use soft delete to preserve security and attendance history. Re-adding the same relationship later creates a completely new row rather than reusing the old one.