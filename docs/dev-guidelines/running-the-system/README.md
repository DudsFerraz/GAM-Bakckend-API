# Running the System

Use these guides from the repository root when running the GAM API locally.

Prefer the Maven Wrapper commands shown here on Windows PowerShell. If you intentionally use a globally installed Maven, replace `.\mvnw.cmd` with `mvn`.

## Pages

- [Prerequisites](prerequisites.md): local tools, Java version, and required environment variables.
- [Start the Backend](start-the-backend.md): how to start the API with the development profile.
- [Maven Commands](maven-commands.md): useful build, test, package, and Spring Boot commands.
- [Docker Compose](docker-compose.md): PostgreSQL container commands and local database details.
- [Dependency and Security Checks](dependency-and-security-checks.md): dependency trees and OWASP dependency-check reports.
- [Common Workflows](common-workflows.md): command sequences for everyday local development.

## Quick Start

Set a local JWT secret:

```powershell
$bytes = New-Object byte[] 32
[System.Security.Cryptography.RandomNumberGenerator]::Fill($bytes)
$env:JWT_SECRET_KEY = [Convert]::ToBase64String($bytes)
```

Start the backend:

```powershell
.\mvnw.cmd -Pdev
```

Run a fast test check:

```powershell
.\mvnw.cmd test
```
