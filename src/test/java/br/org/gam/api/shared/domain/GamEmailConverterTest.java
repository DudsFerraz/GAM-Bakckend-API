package br.org.gam.api.shared.domain;

import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.StructuralTest;
import br.org.gam.api.testing.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@DisplayName("GamEmail Spring Converter")
class GamEmailConverterTest {

    private final GamEmailConverter converter = new GamEmailConverter();

    @Nested
    @FunctionalTest
    @DisplayName("Functional")
    class Functional {

        @Test
        @DisplayName("EP - valid source -> normalized email")
        void validSourceShouldReturnEmail() {
            GamEmail email = converter.convert(" USER@example.com ");

            assertThat(email.value()).isEqualTo("user@example.com");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   ", "\t", "not-an-email"})
        @DisplayName("EP - invalid source -> conversion error")
        void invalidSourceShouldReturnConversionError(String source) {
            assertThatThrownBy(() -> converter.convert(source))
                    .isInstanceOfAny(IllegalArgumentException.class, NullPointerException.class);
        }
    }

    @Nested
    @StructuralTest
    @DisplayName("Structural")
    class Structural {

        @Test
        @DisplayName("COND - converter -> Spring component")
        void converterShouldBeSpringComponent() {
            assertThat(GamEmailConverter.class).hasAnnotation(Component.class);
        }
    }
}
