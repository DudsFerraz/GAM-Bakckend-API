package br.org.gam.api.Entities.event.controller;

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

@Component("eventSpecificationFilterConverter")
class SpecificationFilterConverter {
    private static final Map<String, Function<String, Object>> PARSER_MAP = new HashMap<>();

    static {
        PARSER_MAP.put("id", UUID::fromString);
        PARSER_MAP.put("title", val -> val);
        PARSER_MAP.put("description", val -> val);
        PARSER_MAP.put("location.id", UUID::fromString);
        PARSER_MAP.put("beginDate", OffsetDateTime::parse);
        PARSER_MAP.put("endDate", OffsetDateTime::parse);
        PARSER_MAP.put("createdAt", OffsetDateTime::parse);
        PARSER_MAP.put("updatedAt", OffsetDateTime::parse);
    }

    public List<SpecificationFilter> convert(List<SpecificationFilterDTO> dtos) {
        return GenericSpecificationFilterConverter.convert(dtos, PARSER_MAP);
    }
}
