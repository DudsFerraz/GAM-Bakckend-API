package br.org.gam.api.api;

import br.org.gam.api.testing.annotation.ApiTest;
import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.IntegrationTest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ApiTest
@FunctionalTest
@IntegrationTest
@DisplayName("API - OpenAPI shared schemas")
class OpenApiSharedSchemasApiIT extends AbstractOpenApiDocumentationApiIT {

    @Test
    @DisplayName("REQ-OPENAPI-006 - generated ApiErrorDTO schema -> exact five-field envelope without error reason phrase")
    void apiErrorSchemaShouldExposeOnlyTheCommonFiveFieldEnvelope() {
        Map<String, Object> schemas = schemas();
        Map<String, Object> error = object(schemas, "ApiErrorDTO");
        assertThat(error).isNotNull();
        Map<String, Object> properties = object(error, "properties");

        assertThat(properties).containsOnlyKeys("timestamp", "status", "code", "message", "details");
        assertThat(object(properties, "timestamp"))
                .containsEntry("type", "string")
                .containsEntry("format", "date-time");
        assertThat(object(properties, "status")).containsEntry("type", "integer");
        assertThat(object(properties, "details")).containsEntry("type", "object");
    }

    @Test
    @DisplayName("REQ-OPENAPI-007 - paged GamLocation response schema -> GAM envelope rather than Spring Page internals")
    void pagedGamLocationResponseSchemaShouldUseTheGAMOwnedEnvelope() {
        Map<String, Object> contract = openApiContract().jsonPath().getMap("$");
        Map<String, Object> paths = object(contract, "paths");
        Map<String, Object> operation = object(object(paths, "/gam-locations"), "get");
        Map<String, Object> responses = object(operation, "responses");
        Map<String, Object> response = object(responses, "200");
        Map<String, Object> mediaType = first(object(response, "content").values());
        Map<String, Object> schema = resolveSchema(contract, object(mediaType, "schema"));

        assertThat(object(schema, "properties")).containsOnlyKeys(
                "items", "page", "size", "totalElements", "totalPages", "first", "last"
        );
    }

    @Test
    @DisplayName("REQ-OPENAPI-004 and REQ-OPENAPI-008 - common response schemas -> UUID/date/timestamp/enum/nullability contract")
    void commonResponseSchemasShouldUseStableConsumerRepresentations() {
        Map<String, Object> schemas = schemas();
        Map<String, Object> member = object(schemas, "MemberRDTO");
        Map<String, Object> event = object(schemas, "EventRDTO");
        Map<String, Object> gamLocation = object(schemas, "GamLocationRDTO");

        assertThat(object(object(member, "properties"), "id"))
                .containsEntry("type", "string")
                .containsEntry("format", "uuid");
        assertThat(object(object(member, "properties"), "birthDate"))
                .containsEntry("type", "string")
                .containsEntry("format", "date");
        assertThat(object(object(event, "properties"), "beginDate"))
                .containsEntry("type", "string")
                .containsEntry("format", "date-time");
        Map<String, Object> eventType = object(object(event, "properties"), "type");
        Object eventTypeValues = eventType.get("enum");
        if (eventTypeValues == null && eventType.get("$ref") != null) {
            String reference = eventType.get("$ref").toString();
            eventTypeValues = object(schemas, reference.substring(reference.lastIndexOf('/') + 1)).get("enum");
        }
        assertThat(eventTypeValues).isInstanceOf(List.class);
        assertThat((List<?>) eventTypeValues)
                .allSatisfy(value -> assertThat(value.toString()).matches("[A-Z][A-Z0-9_]*"));
        Object requiredValue = gamLocation.get("required");
        assertThat(requiredValue).isInstanceOf(List.class);
        List<?> required = (List<?>) requiredValue;
        assertThat(required).allSatisfy(value -> assertThat(value).isInstanceOf(String.class));
        assertThat(required.stream().map(String.class::cast).toList())
                .containsExactlyInAnyOrder(
                        "id", "name", "street", "city", "state", "postalCode",
                        "countryCode", "latitude", "longitude"
                );

        Map<String, Object> gamLocationProperties = object(gamLocation, "properties");
        assertNullType(object(gamLocationProperties, "street"));
        assertNullType(object(gamLocationProperties, "postalCode"));
        assertNullType(object(gamLocationProperties, "latitude"));
        assertNullType(object(gamLocationProperties, "longitude"));
    }

    @Test
    @DisplayName("REQ-GAM-LOCATION-002 and REQ-GAM-LOCATION-004 - GamLocation mutation schema -> lengths, ranges, and precision")
    void gamLocationMutationSchemaShouldExposeValidationConstraints() {
        Map<String, Object> properties = object(
                object(schemas(), "GamLocationMutationDTO"), "properties"
        );

        assertLength(properties, "name", 1, 255);
        assertLength(properties, "street", 1, 255);
        assertLength(properties, "city", 1, 100);
        assertLength(properties, "state", 1, 50);
        assertLength(properties, "postalCode", 1, 20);
        assertLength(properties, "countryCode", 2, 2);
        assertThat(object(properties, "countryCode")).containsKey("pattern");

        assertNullType(object(properties, "street"));
        assertNullType(object(properties, "postalCode"));
        assertNullType(object(properties, "latitude"));
        assertNullType(object(properties, "longitude"));

        assertCoordinate(object(properties, "latitude"), -90, 90);
        assertCoordinate(object(properties, "longitude"), -180, 180);
    }

    @Test
    @DisplayName("REQ-GAM-LOCATION-010 - removal schema -> reason length boundary")
    void gamLocationRemovalSchemaShouldExposeReasonLengthBoundary() {
        Map<String, Object> properties = object(object(schemas(), "RemoveGamLocationDTO"), "properties");

        assertLength(properties, "reason", 1, 2_000);
    }

    @Test
    @DisplayName("REQ-GAM-LOCATION-005 and REQ-GAM-LOCATION-006 - GamLocation operations -> specific conflict codes")
    void gamLocationOperationsShouldDocumentSpecificConflictCodes() {
        Map<String, Object> contract = openApiContract().jsonPath().getMap("$");
        Map<String, Object> paths = object(contract, "paths");

        assertConflictCode(object(object(paths, "/gam-locations"), "post"), "GAM_LOCATION_ALREADY_EXISTS");
        assertConflictCode(object(object(paths, "/gam-locations/{id}"), "put"), "GAM_LOCATION_ALREADY_EXISTS");
        assertConflictCode(object(object(paths, "/gam-locations/{id}"), "delete"), "GAM_LOCATION_IN_USE");
    }

    @Test
    @DisplayName("REQ-GAM-LOCATION-005 - GamLocation creation -> Location response header")
    void gamLocationCreationShouldDocumentLocationResponseHeader() {
        Map<String, Object> contract = openApiContract().jsonPath().getMap("$");
        Map<String, Object> paths = object(contract, "paths");
        Map<String, Object> create = object(object(paths, "/gam-locations"), "post");
        Map<String, Object> created = object(object(create, "responses"), "201");
        Map<String, Object> headers = object(created, "headers");

        assertThat(headers).containsKey("Location");
        assertThat(object(headers, "Location")).satisfies(header ->
                assertThat(object(header, "schema")).containsEntry("type", "string")
        );
    }

    private Map<String, Object> schemas() {
        Map<String, Object> contract = openApiContract().jsonPath().getMap("$");
        return object(object(contract, "components"), "schemas");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> first(Collection<Object> values) {
        return (Map<String, Object>) values.iterator().next();
    }

    private Map<String, Object> resolveSchema(Map<String, Object> contract, Map<String, Object> schema) {
        String reference = String.valueOf(schema.get("$ref"));
        String schemaName = reference.substring(reference.lastIndexOf('/') + 1);
        return object(object(object(contract, "components"), "schemas"), schemaName);
    }

    private void assertLength(Map<String, Object> properties, String property, int min, int max) {
        assertThat(object(properties, property))
                .containsEntry("minLength", min)
                .containsEntry("maxLength", max);
    }

    private void assertCoordinate(Map<String, Object> property, double minimum, double maximum) {
        assertThat(((Number) property.get("minimum")).doubleValue()).isEqualTo(minimum);
        assertThat(((Number) property.get("maximum")).doubleValue()).isEqualTo(maximum);
        assertThat(property).containsKey("multipleOf");
    }

    private void assertNullType(Map<String, Object> property) {
        boolean nullableType = property.get("type") instanceof List<?> types && types.contains("null");
        assertThat(nullableType)
                .as("nullable OpenAPI schema property: %s", property)
                .isTrue();
    }

    private void assertConflictCode(Map<String, Object> operation, String expectedCode) {
        Map<String, Object> response = object(object(operation, "responses"), "409");
        Map<String, Object> content = object(response, "content");
        Map<String, Object> mediaType = first(content.values());
        Map<String, Object> example = object(mediaType, "example");
        assertThat(example).containsEntry("code", expectedCode);
    }
}
