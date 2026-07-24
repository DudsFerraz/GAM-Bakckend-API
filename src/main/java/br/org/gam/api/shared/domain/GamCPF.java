package br.org.gam.api.shared.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public record GamCPF(String value) {
    private static final Pattern UNFORMATTED_PATTERN = Pattern.compile("[0-9]{11}");
    private static final Pattern FORMATTED_PATTERN = Pattern.compile(
            "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}"
    );

    public GamCPF {
        Objects.requireNonNull(value, "CPF cannot be null");
        value = value.strip();

        if (!UNFORMATTED_PATTERN.matcher(value).matches()
                && !FORMATTED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("CPF has invalid syntax");
        }

        value = value.replace(".", "").replace("-", "");

        if (hasRepeatedDigits(value) || !hasValidCheckDigits(value)) {
            throw new IllegalArgumentException("CPF has invalid check digits");
        }
    }

    private static boolean hasRepeatedDigits(String value) {
        return value.chars().allMatch(character -> character == value.charAt(0));
    }

    private static boolean hasValidCheckDigits(String value) {
        int firstCheckDigit = calculateCheckDigit(value, 9, 10);
        if (firstCheckDigit != digitAt(value, 9)) {
            return false;
        }

        int secondCheckDigit = calculateCheckDigit(value, 10, 11);
        return secondCheckDigit == digitAt(value, 10);
    }

    private static int calculateCheckDigit(String value, int digitCount, int initialWeight) {
        int sum = 0;
        for (int index = 0; index < digitCount; index++) {
            sum += digitAt(value, index) * (initialWeight - index);
        }

        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    private static int digitAt(String value, int index) {
        return value.charAt(index) - '0';
    }
}
