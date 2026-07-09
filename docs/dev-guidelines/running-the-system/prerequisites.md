# Prerequisites

Use this guide from the repository root.

## Required Tools

- JDK 21 with `JAVA_HOME` pointing to the JDK.
- Docker Desktop running.
- PowerShell.
- The repository checked out locally.

Check the Java version:

```powershell
java -version
```

The build enforces Java 21.

## Maven Wrapper

Use the included Maven Wrapper by default:

```powershell
.\mvnw.cmd --version
```

If you intentionally use a globally installed Maven, replace `.\mvnw.cmd` with `mvn` in the other running-system guides.

## Local Environment Variables

The development profile requires `JWT_SECRET_KEY`.

Create a local-only base64 secret with at least 32 decoded bytes:

```powershell
$bytes = New-Object byte[] 32
[System.Security.Cryptography.RandomNumberGenerator]::Fill($bytes)
$env:JWT_SECRET_KEY = [Convert]::ToBase64String($bytes)
```

Set this in each new shell before starting the application, or store it in a local development secret manager.

Do not commit local secrets.
