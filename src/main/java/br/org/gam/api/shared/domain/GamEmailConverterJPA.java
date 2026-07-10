package br.org.gam.api.shared.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GamEmailConverterJPA implements AttributeConverter<GamEmail, String> {

    @Override
    public String convertToDatabaseColumn(GamEmail email) {
        if (email == null) return null;

        return email.value();
    }

    @Override
    public GamEmail convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;

        return GamEmail.of(dbData);
    }
}
