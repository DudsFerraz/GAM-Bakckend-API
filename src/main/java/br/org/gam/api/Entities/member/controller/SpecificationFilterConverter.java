package br.org.gam.api.Entities.member.controller;

import br.org.gam.api.Entities.account.common.PermissionLevelEnum;
import br.org.gam.api.Entities.member.common.MemberStatusEnum;
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

@Component
class SpecificationFilterConverter {
    private static final Map<String, Function<String, Object>> PARSER_MAP = new HashMap<>();

    static {
        PARSER_MAP.put("id", UUID::fromString);
        PARSER_MAP.put("account.id", UUID::fromString);
        PARSER_MAP.put("firstName", val -> val);
        PARSER_MAP.put("surname", val -> val);
        PARSER_MAP.put("birthDate", OffsetDateTime::parse);
        PARSER_MAP.put("phoneNumber", val -> val);
        PARSER_MAP.put("status", val -> MemberStatusEnum.valueOf(val.toUpperCase()));
        PARSER_MAP.put("createdAt", OffsetDateTime::parse);
        PARSER_MAP.put("updatedAt", OffsetDateTime::parse);
    }

    public List<SpecificationFilter> convert(List<SpecificationFilterDTO> dtos) {
        return GenericSpecificationFilterConverter.convert(dtos, PARSER_MAP);
    }
}
