package br.org.gam.api.Entities.location.services.getLocationById;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

public record GetLocationByIdDTO(
        UUID id,
        String name,
        String street,
        String city,
        String state,
        String postalCode,
        String countryCode,
        @Nullable BigDecimal latitude,
        @Nullable BigDecimal longitude
) {
}
