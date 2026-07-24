package br.org.gam.api.shared.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public record GamRG(String value) {
    private static final int MAX_LENGTH = 20;
    private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("[A-Za-z0-9 ./-]+");
    private static final Pattern ASCII_LETTER_OR_DIGIT = Pattern.compile("[A-Za-z0-9]");

    public GamRG {
        Objects.requireNonNull(value, "RG cannot be null");

        if (!ALLOWED_CHARACTERS.matcher(value).matches()) {
            throw new IllegalArgumentException("RG contains invalid characters");
        }

        value = value.trim();

        if (value.isEmpty()) {
            throw new IllegalArgumentException("RG cannot be blank");
        }

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("RG cannot exceed 20 characters");
        }

        if (!ASCII_LETTER_OR_DIGIT.matcher(value).find()) {
            throw new IllegalArgumentException("RG must contain an ASCII letter or digit");
        }
    }
}
