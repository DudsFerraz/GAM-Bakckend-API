package br.org.gam.api.account;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter()
public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email email) {
        if (email == null) return null;

        return email.value();
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;

        return new Email(dbData);
    }
}
