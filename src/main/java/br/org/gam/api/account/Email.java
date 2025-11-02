package br.org.gam.api.account;

import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public Email {
        if (value == null || !EMAIL_REGEX.matcher(value).matches()) {
            throw new IllegalArgumentException("Formato de e-mail inválido: " + value);
        }
    }

}