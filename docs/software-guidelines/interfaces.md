# Interfaces Guidelines

## 1. Purpose

This document defines the rules for using interfaces and concrete classes in `gam-api`.

In this codebase, **interfaces represent real architectural boundaries, not default ceremonies**. Application workflows and operations are built as concrete classes by default. We do not use the "one interface, one implementation" pattern for application services.

---

## 2. The Core Rule

An interface is only justified if it isolates the application from an external system, fulfills a framework contract, or provides genuine polymorphism.

Do **not** create an interface for any of the following reasons:

* It is considered a "standard Java practice."
* A class is annotated with `@Service`.
* You anticipate a *hypothetical* second implementation in the future.
* You believe it is required for unit testing.

### Testing Without Interfaces

Concrete application classes are perfectly testable without dedicated interfaces. You can isolate the class under test by passing mocks, fakes, or test doubles directly into its constructor.

```java
// Testing a concrete class directly by injecting mocked dependencies
RegisterMember registerMember = new RegisterMember(
        mockMemberRepository,
        mockMemberMapper,
        mockAccountDomainLoader
);

```

---

## 3. Forbidden Patterns

### Application Service Interfaces

Do not create interfaces for single-implementation application workflows or read operations. This doubles the number of files without adding flexibility and leads to awkward, framework-coupled naming conventions.

* **Anti-Pattern:** Creating a `RegisterMember` interface and a `SpringRegisterMember` implementation class.
* **Correct Approach:** Create a single, concrete class named `RegisterMember`.

---

## 4. Valid Interface Use Cases

You **must** use an interface when your code falls into one of the following categories.

### 4.1. Framework-Generated Implementations

When a framework generates the implementation at runtime or compile time, the interface is the required contract.

* **Spring Data Repositories:** `MemberRepository`, `AccountRepository`.
* **MapStruct Mappers:** `MemberMapper`, `EventMapper`.

### 4.2. Framework Contracts

When the application must plug into an external framework's ecosystem, you must implement the framework's interfaces.

* **Spring Security:** Implementing `UserDetails` or `UserDetailsService`.
* **Spring Web:** Implementing `AuthenticationEntryPoint`.
* **JPA:** Implementing `AttributeConverter<MyEmail, String>`.

### 4.3. Multiple Real Implementations

When multiple implementations *actually exist* today (or are being built in the current feature branch), an interface is required to enable polymorphism.

```java
// A valid interface because multiple real strategies exist
public interface NotificationSender {
    void send(Notification notification);
}

public class EmailNotificationSender implements NotificationSender { ... }
public class WhatsAppNotificationSender implements NotificationSender { ... }

```

### 4.4. External Infrastructure Boundaries

Interfaces are required to protect application and domain logic from third-party APIs or external providers. If an external service goes down or is replaced, the application layer should only depend on the interface.

* **Example:** A `PaymentGateway` interface implemented by `StripePaymentGateway`.

### 4.5. Strategy or Algorithm Variation

When a specific business behavior is genuinely interchangeable at runtime or configuration time, use an interface to define the strategy.

* **Example:** A `PresenceConflictResolver` interface implemented by `StrictPresenceConflictResolver` and `LenientPresenceConflictResolver`.

### 4.6. Shared Infrastructure Contracts

When generic, cross-cutting infrastructure needs a stable contract across many different domain objects, use an interface.

* **Example:** A `SoftDeletable` interface that ensures generic repositories know how to safely soft-delete various entity types.