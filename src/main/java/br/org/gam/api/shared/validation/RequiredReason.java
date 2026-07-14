package br.org.gam.api.shared.validation;

import br.org.gam.api.shared.exception.InvalidCommandException;

public final class RequiredReason {
    private static final int MAX_CODE_POINTS = 2_000;

    private RequiredReason() {
    }

    public static String normalize(String reason, String message) {
        if (reason == null) {
            throw InvalidCommandException.reason(message);
        }
        String normalized = reason.strip();
        if (normalized.isEmpty()
                || normalized.codePointCount(0, normalized.length()) > MAX_CODE_POINTS) {
            throw InvalidCommandException.reason(message);
        }
        return normalized;
    }
}
