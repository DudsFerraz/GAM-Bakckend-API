package br.org.gam.api.Entities.account.controller;

import br.org.gam.api.common.PermissionLevelEnum;
import br.org.gam.api.common.specification.GenericSpecificationFilterConverter;
import br.org.gam.api.common.specification.SpecificationFilter;
import br.org.gam.api.common.specification.SpecificationFilterDTO;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component("accountSpecificationFilterConverter")
class SpecificationFilterConverter {
    private static final Map<String, Function<String, Object>> PARSER_MAP = new HashMap<>();

    static {
        PARSER_MAP.put("id", UUID::fromString);
        PARSER_MAP.put("displayName", val -> val);
        PARSER_MAP.put("email", val -> val);
        PARSER_MAP.put("permissionLevel", val -> PermissionLevelEnum.valueOf(val.toUpperCase()));
        PARSER_MAP.put("createdAt", OffsetDateTime::parse);
        PARSER_MAP.put("updatedAt", OffsetDateTime::parse);
    }

    public List<SpecificationFilter> convert(List<SpecificationFilterDTO> dtos) {
        return GenericSpecificationFilterConverter.convert(dtos, PARSER_MAP);
    }
}
