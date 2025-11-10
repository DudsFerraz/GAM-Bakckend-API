package br.org.gam.api.Entities.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public Email {
        if (value == null || !EMAIL_REGEX.matcher(value).matches()) {
            throw new IllegalArgumentException("Formato de e-mail inválido: " + value);
        }
    }

    @Override
    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static Email of(String value) {
        return new Email(value);
    }

}