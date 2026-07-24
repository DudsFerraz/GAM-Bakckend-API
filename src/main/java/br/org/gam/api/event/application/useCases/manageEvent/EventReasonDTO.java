package br.org.gam.api.event.application.useCases.manageEvent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = false)
public record EventReasonDTO(
        @NotNull @NotBlank @Size(max = 2_000) String reason
) {
}
