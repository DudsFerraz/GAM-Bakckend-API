package br.org.gam.api.shared.phonenumber;

import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@DisplayName("GamPhoneNumber JPA Converter")
class GamPhoneNumberConverterJPATest {

    private final GamPhoneNumberConverterJPA converter = new GamPhoneNumberConverterJPA();

    @Nested
    @FunctionalTest
    @DisplayName("Functional")
    class Functional {

        @Test
        @DisplayName("EP - phone number -> database E.164 value")
        void phoneNumberShouldConvertToDatabaseValue() {
            GamPhoneNumber phoneNumber = GamPhoneNumber.fromString("+5519998877665");

            assertThat(converter.convertToDatabaseColumn(phoneNumber)).isEqualTo("+5519998877665");
        }

        @Test
        @DisplayName("EP - database E.164 value -> phone number")
        void databaseValueShouldConvertToPhoneNumber() {
            GamPhoneNumber phoneNumber = converter.convertToEntityAttribute("+5519998877665");

            assertThat(phoneNumber.value()).isEqualTo("+5519998877665");
        }

        @Test
        @DisplayName("EP - null phone number -> null database value")
        void nullPhoneNumberShouldConvertToNullDatabaseValue() {
            assertThat(converter.convertToDatabaseColumn(null)).isNull();
        }

        @Test
        @DisplayName("EP - null database value -> null phone number")
        void nullDatabaseValueShouldConvertToNullPhoneNumber() {
            assertThat(converter.convertToEntityAttribute(null)).isNull();
        }
    }
}
