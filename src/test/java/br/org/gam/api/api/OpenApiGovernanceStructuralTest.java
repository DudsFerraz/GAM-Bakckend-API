package br.org.gam.api.api;

import br.org.gam.api.testing.annotation.StructuralTest;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import static org.assertj.core.api.Assertions.assertThat;

@StructuralTest
@DisplayName("Structure - OpenAPI governance workflow")
class OpenApiGovernanceStructuralTest {

    private static final long DEFAULT_START_WAIT_MILLIS = 500;
    private static final long MINIMUM_STARTUP_WINDOW_MILLIS = 60_000;

    @Test
    @DisplayName("REQ-OPENAPI-009 - Maven build -> stable openapi profile orchestrates contract generation")
    void buildShouldProvideTheOpenApiProfile() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try (InputStream pom = Files.newInputStream(Path.of("pom.xml"))) {
            Document document = factory.newDocumentBuilder().parse(pom);
            XPath xpath = XPathFactory.newInstance().newXPath();
            Node profile = (Node) xpath.evaluate(
                    "/project/profiles/profile[id='openapi']",
                    document,
                    XPathConstants.NODE
            );

            assertThat(profile).isNotNull();
        }
    }

    @Test
    @DisplayName("REQ-OPENAPI-012 - canonical generation command -> repository-owned startup window")
    void openApiProfileShouldAllowApplicationStartupWithoutExternalOverrides() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try (InputStream pom = Files.newInputStream(Path.of("pom.xml"))) {
            Document document = factory.newDocumentBuilder().parse(pom);
            XPath xpath = XPathFactory.newInstance().newXPath();
            String pluginPath = "/project/profiles/profile[id='openapi']/build/plugins/plugin["
                    + "artifactId='spring-boot-maven-plugin']/configuration/";
            String maxAttemptsText = xpath.evaluate(pluginPath + "maxAttempts", document).trim();
            String waitText = xpath.evaluate(pluginPath + "wait", document).trim();

            assertThat(maxAttemptsText).isNotBlank();
            long maxAttempts = Long.parseLong(maxAttemptsText);
            long waitMillis = waitText.isBlank() ? DEFAULT_START_WAIT_MILLIS : Long.parseLong(waitText);

            assertThat(maxAttempts * waitMillis).isGreaterThanOrEqualTo(MINIMUM_STARTUP_WINDOW_MILLIS);
        }
    }

    @Test
    @DisplayName("REQ-OPENAPI-011 - repository automation -> contract generation, Spectral, oasdiff, and release artifact checks")
    void repositoryAutomationShouldGovernTheGeneratedContract() throws Exception {
        List<Path> workflows;
        try (var files = Files.list(Path.of(".github", "workflows"))) {
            workflows = files.filter(Files::isRegularFile).toList();
        }
        String automation = workflows.stream()
                .map(this::read)
                .reduce("", String::concat)
                .toLowerCase();

        assertThat(automation).contains("-popenapi verify", "spectral", "oasdiff", "openapi.yaml");
        assertThat(Files.exists(Path.of(".spectral.yaml"))).isTrue();
    }

    private String read(Path file) {
        try {
            return Files.readString(file);
        } catch (Exception exception) {
            throw new AssertionError("Could not read workflow " + file, exception);
        }
    }
}
