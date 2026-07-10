package br.org.gam.api.account.application;

import br.org.gam.api.rbac.accountRole.application.AccountRolesRDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;

public class AccountRolesSerializer extends JsonSerializer<AccountRolesRDTO> {

    @Override
    public void serialize(AccountRolesRDTO value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        serializers.defaultSerializeValue(value == null ? List.of() : value.roles(), generator);
    }
}
