package br.org.gam.api.exception;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

public record ApiErrorDTO(
        int status,
        String error,
        String message,
        String timestamp
) {
    public ApiErrorDTO(HttpStatus status, String message) {
        this(
                status.value(),
                status.getReasonPhrase(),
                message,
                OffsetDateTime.now().toString()
        );
    }
}
