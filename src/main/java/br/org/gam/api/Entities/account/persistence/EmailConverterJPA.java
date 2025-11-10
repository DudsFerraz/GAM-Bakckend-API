package br.org.gam.api.Entities.account.persistence;

import br.org.gam.api.Entities.account.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EmailConverterJPA implements AttributeConverter<Email, String> {

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
