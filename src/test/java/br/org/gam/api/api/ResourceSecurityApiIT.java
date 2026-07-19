package br.org.gam.api.api;

import br.org.gam.api.testing.annotation.ApiTest;
import br.org.gam.api.testing.annotation.IntegrationTest;
import br.org.gam.api.testing.annotation.SecurityTest;
import br.org.gam.api.testing.integration.BaseApiIntegrationTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ApiTest
@IntegrationTest
@SecurityTest
@DisplayName("API - Resource Security")
class ResourceSecurityApiIT extends BaseApiIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("protected endpoint without token -> HTTP 401")
    void protectedEndpointWithoutTokenShouldReturnUnauthorized() {
        jsonRequest()
                .get("/accounts/{id}", UUID.randomUUID())
                .then()
                .statusCode(401)
                .body("status", equalTo(401))
                .body("message", containsString("Authentication failed"));
    }

    @Test
    @DisplayName("malformed bearer token -> HTTP 401")
    void malformedBearerTokenShouldReturnUnauthorized() {
        jsonRequest()
                .header("Authorization", "Bearer not-a-jwt")
                .get("/accounts/{id}", UUID.randomUUID())
                .then()
                .statusCode(401)
                .body("status", equalTo(401))
                .body("message", containsString("Authentication failed"));
    }

    @Test
    @DisplayName("bearer token for deleted Account -> HTTP 401")
    void bearerTokenForDeletedAccountShouldReturnUnauthorized() {
        AuthSession session = registerAndLogin(null);
        softDeleteAccount(session.accountId());

        jsonRequest()
                .header("Authorization", "Bearer " + session.accessToken())
                .get("/accounts/{id}", session.accountId())
                .then()
                .statusCode(401)
                .body("status", equalTo(401))
                .body("message", containsString("Authentication failed"));
    }

    @Test
    @DisplayName("missing permission -> HTTP 403")
    void missingPermissionShouldReturnForbidden() {
        AuthSession coord = registerAndLogin("COORD");
        UUID gamLocationId = createGamLocationForResourceSecurity(coord, "Forbidden Event Location");
        AuthSession member = registerAndLogin("MEMBER");
        UUID requiredPermissionId = permissionId("EVENT_GET_COORD");

        authenticatedJsonRequest(member)
                .body(eventPayload("Forbidden Event", gamLocationId, requiredPermissionId))
                .post("/events")
                .then()
                .statusCode(403)
                .body("status", equalTo(403))
                .body("message", containsString("Access denied"));
    }

    @Test
    @DisplayName("not found resource -> HTTP 404")
    void notFoundResourceShouldReturnNotFound() {
        AuthSession coord = registerAndLogin("COORD");

        authenticatedJsonRequest(coord)
                .get("/accounts/{id}", UUID.randomUUID())
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("code", equalTo("RESOURCE_NOT_FOUND"));
    }

    @Test
    @DisplayName("valid GamLocation request -> HTTP 201, Location header, and persisted row")
    void validGamLocationRequestShouldReturnCreatedPayloadAndPersistRow() {
        AuthSession coord = registerAndLogin("COORD");

        ExtractableResponse<Response> response = withUntrustedForwardingHeaders(authenticatedJsonRequest(coord))
                .body(gamLocationPayload("API GamLocation"))
                .post("/gam-locations")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .extract();

        UUID gamLocationId = UUID.fromString(response.path("id"));
        assertPublicApiLocation(response, "/gam-locations/" + gamLocationId);
        trackGamLocation(gamLocationId);
        assertThat(gamLocationExists(gamLocationId)).isTrue();
    }

    @Test
    @DisplayName("valid event request with permission -> HTTP 201 and persisted row")
    void validEventRequestWithPermissionShouldReturnCreatedPayloadAndPersistRow() {
        AuthSession coord = registerAndLogin("COORD");
        UUID gamLocationId = createGamLocationForResourceSecurity(coord, "API Event Location");
        UUID requiredPermissionId = permissionId("EVENT_GET_COORD");

        ExtractableResponse<Response> response = withUntrustedForwardingHeaders(authenticatedJsonRequest(coord))
                .body(eventPayload("API Event", gamLocationId, requiredPermissionId))
                .post("/events")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .extract();

        UUID eventId = UUID.fromString(response.path("id"));
        assertPublicApiLocation(response, "/events/" + eventId);
        trackEvent(eventId);
        assertThat(eventExists(eventId)).isTrue();
        assertThat(jdbcTemplate.queryForObject(
                "SELECT metadata ->> 'gamLocationId' FROM activity_logs "
                        + "WHERE action = 'EVENT_CREATED' AND target_id = ?",
                String.class,
                eventId
        )).isEqualTo(gamLocationId.toString());
        assertThat(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM activity_logs "
                        + "WHERE action = 'EVENT_CREATED' AND target_id = ? "
                        + "AND jsonb_exists(metadata, 'locationId')",
                Long.class,
                eventId
        )).isZero();
    }

    @Test
    @DisplayName("invalid event payload -> HTTP 400")
    void invalidEventPayloadShouldReturnBadRequest() {
        AuthSession coord = registerAndLogin("COORD");
        UUID gamLocationId = createGamLocationForResourceSecurity(coord, "Invalid Event Location");
        UUID requiredPermissionId = permissionId("EVENT_GET_COORD");
        Map<String, Object> payload = eventPayload("Invalid Event", gamLocationId, requiredPermissionId);
        payload = new java.util.HashMap<>(payload);
        payload.put("title", "");

        authenticatedJsonRequest(coord)
                .body(payload)
                .post("/events")
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("message", containsString("Validation error"));
    }

    @Test
    @DisplayName("client-supplied Event type -> HTTP 400")
    void clientSuppliedEventTypeShouldReturnBadRequest() {
        AuthSession coord = registerAndLogin("COORD");
        UUID gamLocationId = createGamLocationForResourceSecurity(coord, "Client Type Location");
        UUID requiredPermissionId = permissionId("EVENT_GET_COORD");
        Map<String, Object> payload = new java.util.HashMap<>(
                eventPayload("Client Type Event", gamLocationId, requiredPermissionId)
        );
        payload.put("type", "MISSA");

        authenticatedJsonRequest(coord)
                .body(payload)
                .post("/events")
                .then()
                .statusCode(400)
                .body("status", equalTo(400));
    }
}
