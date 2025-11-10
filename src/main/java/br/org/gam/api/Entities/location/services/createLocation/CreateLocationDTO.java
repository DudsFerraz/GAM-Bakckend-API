package br.org.gam.api.Entities.location.services.createLocation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

public record CreateLocationDTO(
        @NotNull @NotBlank String name,
        @Nullable String street,
        @NotNull @NotBlank String city,
        @NotNull @NotBlank String state,
        @Nullable String postalCode,
        @NotNull @NotBlank String countryCode,
        @Nullable BigDecimal latitude,
        @Nullable BigDecimal longitude
) {
}
