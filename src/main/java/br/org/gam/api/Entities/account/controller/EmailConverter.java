package br.org.gam.api.Entities.account.controller;

import br.org.gam.api.Entities.account.common.Email;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class EmailConverter implements Converter<String, Email> {
    @Override
    public Email convert(String source) {
        if (source == null || source.isBlank()) return null;

        return new Email(source);
    }
}
