package br.org.gam.api.common.specification;

import java.util.List;

public record SearchDTO(
        List<SpecificationFilterDTO> filters
) {
}
