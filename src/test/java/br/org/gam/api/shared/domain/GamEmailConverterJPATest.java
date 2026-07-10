package br.org.gam.api.shared.domain;

import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.StructuralTest;
import br.org.gam.api.testing.annotation.UnitTest;
import jakarta.persistence.Converter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@DisplayName("GamEmail JPA Converter")
class GamEmailConverterJPATest {

    private final GamEmailConverterJPA converter = new GamEmailConverterJPA();

    @Nested
    @FunctionalTest
    @DisplayName("Functional")
    class Functional {

        @Test
        @DisplayName("EP - email value object -> database value")
        void emailValueObjectShouldReturnDatabaseValue() {
            assertThat(converter.convertToDatabaseColumn(GamEmail.of("user@example.com"))).isEqualTo("user@example.com");
        }

        @Test
        @DisplayName("EP - database value -> email value object")
        void databaseValueShouldReturnEmailValueObject() {
            GamEmail email = converter.convertToEntityAttribute("USER@example.com");

            assertThat(email.value()).isEqualTo("user@example.com");
        }

        @Test
        @DisplayName("EP - null email value object -> null database value")
        void nullEmailValueObjectShouldReturnNullDatabaseValue() {
            assertThat(converter.convertToDatabaseColumn(null)).isNull();
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {" ", "   ", "\t"})
        @DisplayName("EP - blank database value -> null email")
        void blankDatabaseValueShouldReturnNullEmail(String dbData) {
            assertThat(converter.convertToEntityAttribute(dbData)).isNull();
        }

        @Test
        @DisplayName("EP - invalid database value -> conversion error")
        void invalidDatabaseValueShouldReturnConversionError() {
            assertThatThrownBy(() -> converter.convertToEntityAttribute("not-an-email"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @StructuralTest
    @DisplayName("Structural")
    class Structural {

        @Test
        @DisplayName("COND - JPA converter -> auto-apply converter")
        void jpaConverterShouldBeAutoApplyConverter() {
            Converter annotation = GamEmailConverterJPA.class.getAnnotation(Converter.class);

            assertThat(annotation).isNotNull();
            assertThat(annotation.autoApply()).isTrue();
        }
    }
}
