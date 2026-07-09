# Dependency and Security Checks

Use these commands from the repository root.

## Dependency Trees

Write the full Maven dependency tree to a text file:

```powershell
.\mvnw.cmd dependency:tree "-DoutputFile=target/dependency-tree.txt" "-DoutputType=text"
```

This creates `target/dependency-tree.txt`, which is useful for reviews, dependency upgrade checks, and vulnerability analysis.

Inspect Logback dependencies:

```powershell
.\mvnw.cmd dependency:tree "-Dincludes=ch.qos.logback"
```

Inspect Apache Commons Lang dependencies:

```powershell
.\mvnw.cmd dependency:tree "-Dincludes=org.apache.commons:commons-lang3"
```

Inspect Apache Commons Compress dependencies:

```powershell
.\mvnw.cmd dependency:tree "-Dincludes=org.apache.commons:commons-compress"
```

Inspect embedded Tomcat dependencies:

```powershell
.\mvnw.cmd dependency:tree "-Dincludes=org.apache.tomcat.embed"
```

Use these filtered trees when checking whether a transitive dependency is present, which version Maven selected, and which starter or library brought it into the project.

## OWASP Dependency-Check

Run OWASP dependency-check and write reports under `target/dependency-check`:

```powershell
.\mvnw.cmd org.owasp:dependency-check-maven:12.1.0:check "-Dformat=ALL" "-DoutputDirectory=target/dependency-check" "-DfailBuildOnCVSS=11"
```

This generates reports such as HTML and JSON under `target/dependency-check`.

The first run can take a long time because dependency-check downloads and processes National Vulnerability Database data. The OWASP dependency-check Maven documentation says the first execution may take 20 minutes or more; after the initial data download, regular runs are much faster when the plugin is executed at least once every seven days.

The `-DfailBuildOnCVSS=11` value keeps the local analysis from failing the build for ordinary CVSS findings, because CVSS scores normally top out at 10. Treat the report as review input and decide fixes intentionally.

Reference: [OWASP dependency-check-maven documentation](https://jeremylong.github.io/DependencyCheck/dependency-check-maven/).
