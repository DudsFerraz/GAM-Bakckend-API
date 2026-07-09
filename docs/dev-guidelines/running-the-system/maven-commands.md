# Maven Commands

Use these commands from the repository root.

## Tests and Verification

Run unit and fast tests:

```powershell
.\mvnw.cmd test
```

This runs the Maven `test` phase. In this project, Surefire excludes tests tagged as `IntegrationTest`, `ApiTest`, or `PersistenceTest`.

Run the full verification suite:

```powershell
.\mvnw.cmd verify
```

This runs unit tests and then the Failsafe integration-test flow for tests tagged as `IntegrationTest`, `ApiTest`, or `PersistenceTest`. Docker Desktop must be available for Testcontainers-backed tests.

Run tests from a clean build directory:

```powershell
.\mvnw.cmd clean test
```

## Build Output

Clean generated build output:

```powershell
.\mvnw.cmd clean
```

Create the packaged application artifact:

```powershell
.\mvnw.cmd package
```

This compiles, tests, and packages the application under `target/`.

Skip tests only for local packaging experiments:

```powershell
.\mvnw.cmd package "-DskipTests"
```

Do not use skipped-test builds as evidence that a change is ready.

## Spring Boot

Run the backend with the project development shortcut:

```powershell
.\mvnw.cmd -Pdev
```

Run the backend through the Spring Boot Maven plugin with the `dev` Spring profile:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Run the backend through the Spring Boot Maven plugin without activating a Spring profile:

```powershell
.\mvnw.cmd spring-boot:run
```

Plain `spring-boot:run` does not load development settings or start Docker Compose unless configuration is supplied another way.
