package br.org.gam.api.presence.application.useCases.registerPresence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.lang.Nullable;

@JsonIgnoreProperties(ignoreUnknown = false)
public record RegisterPresenceRequestDTO(
        @NotNull UUID memberId,
        @Nullable @Schema(types = {"string", "null"}, maxLength = 2_000) String observations
) {
}
