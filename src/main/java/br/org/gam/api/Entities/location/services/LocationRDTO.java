package br.org.gam.api.Entities.location.services;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

public record LocationRDTO(
        UUID id,
        String name,
        @Nullable String street,
        String city,
        String state,
        @Nullable String postalCode,
        String countryCode,
        @Nullable BigDecimal latitude,
        @Nullable BigDecimal longitude
) {
}
