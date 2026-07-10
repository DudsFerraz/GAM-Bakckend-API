package br.org.gam.api.shared.domain;

import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@DisplayName("GamEmail Account Boundary")
class GamEmailAccountBoundaryTest {

    @Nested
    @FunctionalTest
    @DisplayName("Functional")
    class Functional {

        @ParameterizedTest
        @CsvSource({
                "user@example.com, user@example.com",
                "' User.Name+tag@Example.COM ', user.name+tag@example.com"
        })
        @DisplayName("EP - valid account email -> normalized lowercase trimmed value")
        void validAccountEmailShouldNormalizeValue(String rawEmail, String expectedEmail) {
            GamEmail email = GamEmail.of(rawEmail);

            assertThat(email.value()).isEqualTo(expectedEmail);
        }

        @Test
        @DisplayName("EP - string representation -> normalized email value")
        void stringRepresentationShouldReturnNormalizedValue() {
            GamEmail email = GamEmail.of(" USER@example.com ");

            assertThat(email).hasToString("user@example.com");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "not-an-email", "user@", "@example.com"})
        @DisplayName("EP - invalid account email -> validation error")
        void invalidAccountEmailShouldReturnValidationError(String rawEmail) {
            assertThatThrownBy(() -> GamEmail.of(rawEmail))
                    .isInstanceOfAny(IllegalArgumentException.class, NullPointerException.class);
        }
    }
}
