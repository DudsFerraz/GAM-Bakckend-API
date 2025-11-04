package br.org.gam.api.common.specification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SpecificationFilterDTO(
        @NotNull @NotBlank String field,
        @NotNull @NotBlank String value,
        @NotNull ComparationMethodsEnum comparationMethod
) {
}
