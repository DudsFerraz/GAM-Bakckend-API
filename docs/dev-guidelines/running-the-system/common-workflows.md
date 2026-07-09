# Common Workflows

Use these command sequences from the repository root.

## Start a Local Backend Session

```powershell
$env:JWT_SECRET_KEY = "<local-base64-secret>"
.\mvnw.cmd -Pdev
```

## Run a Fast Check

Run this before handing work to another developer or agent:

```powershell
.\mvnw.cmd test
```

## Run Broader Verification

Run this before opening a pull request:

```powershell
.\mvnw.cmd verify
```

## Refresh Dependency Visibility

Use this during dependency or security work:

```powershell
.\mvnw.cmd dependency:tree "-DoutputFile=target/dependency-tree.txt" "-DoutputType=text"
.\mvnw.cmd org.owasp:dependency-check-maven:12.1.0:check "-Dformat=ALL" "-DoutputDirectory=target/dependency-check" "-DfailBuildOnCVSS=11"
```

## Reset a Broken Local Database

This deletes local database data:

```powershell
docker compose down -v
$env:JWT_SECRET_KEY = "<local-base64-secret>"
.\mvnw.cmd -Pdev
```
