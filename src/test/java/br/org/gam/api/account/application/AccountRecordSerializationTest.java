package br.org.gam.api.account.application;

import br.org.gam.api.rbac.accountRole.application.AccountRolesRDTO;
import br.org.gam.api.shared.domain.GamEmail;
import br.org.gam.api.testing.annotation.StructuralTest;
import br.org.gam.api.testing.annotation.UnitTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@StructuralTest
@DisplayName("Account Record Serialization")
class AccountRecordSerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("empty role wrapper -> empty roles array")
    void emptyRoleWrapperShouldSerializeAsEmptyRolesArray() throws Exception {
        JsonNode response = serialize(new AccountRolesRDTO());

        assertThat(response.path("roles").isArray()).isTrue();
        assertThat(response.path("roles")).isEmpty();
    }

    @Test
    @DisplayName("null role wrapper -> empty roles array")
    void nullRoleWrapperShouldSerializeAsEmptyRolesArray() throws Exception {
        JsonNode response = serialize(null);

        assertThat(response.path("roles").isArray()).isTrue();
        assertThat(response.path("roles")).isEmpty();
    }

    private JsonNode serialize(AccountRolesRDTO roles) throws Exception {
        AccountRDTO response = new AccountRDTO(
                UUID.randomUUID(),
                GamEmail.of("record@example.com"),
                "Record",
                roles
        );
        return objectMapper.readTree(objectMapper.writeValueAsString(response));
    }
}
