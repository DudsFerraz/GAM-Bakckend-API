package br.org.gam.api.common.specification;

import jakarta.validation.Valid;

import java.util.List;

public record SearchDTO(
        @Valid List<SpecificationFilterDTO> filters
) {
}
