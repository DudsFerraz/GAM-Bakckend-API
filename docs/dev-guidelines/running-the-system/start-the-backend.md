# Start the Backend

Use this guide from the repository root after completing [Prerequisites](prerequisites.md).

## Development Shortcut

Run the application with the development Maven profile:

```powershell
.\mvnw.cmd -Pdev
```

This profile sets the Spring profile to `dev` and uses the Spring Boot Maven plugin default goal, `spring-boot:run`.

With the `dev` Spring profile active, Spring Boot Docker Compose support can start the PostgreSQL service from `compose.yml` when Docker Desktop is running. The database container stays running after the application exits so later restarts are faster.

The backend starts with Flyway validation and migration enabled. Local development uses PostgreSQL 18 because migrations use PostgreSQL's built-in `uuidv7()` function.

## Explicit Spring Boot Command

Run the backend with the development Spring profile explicitly:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

This is equivalent in intent to using the project development shortcut, but more verbose.

## Plain Spring Boot Command

Run the backend without the project development shortcut:

```powershell
.\mvnw.cmd spring-boot:run
```

This starts the Spring Boot Maven plugin directly. It does not activate the `dev` Spring profile by itself and should only be used when you intentionally provide the required configuration another way.
