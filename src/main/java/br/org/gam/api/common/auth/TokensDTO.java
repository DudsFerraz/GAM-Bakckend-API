package br.org.gam.api.common.auth;

import java.util.UUID;

public record TokensDTO(
        String accessToken,
        UUID refreshToken
) {
}
