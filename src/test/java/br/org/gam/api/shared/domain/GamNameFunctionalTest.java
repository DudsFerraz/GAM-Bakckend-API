package br.org.gam.api.shared.domain;

import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@FunctionalTest
@DisplayName("Functional - GamName Value Object")
class GamNameFunctionalTest {

    @ParameterizedTest
    @CsvSource({
            "Ana, Silva",
            "Al, Ng",
            "Joao Pedro, Oliveira-Santos",
            "Francois, L'Ecuyer"
    })
    @DisplayName("EP - valid Unicode name -> accepted")
    void validUnicodeNameShouldBeAccepted(String firstName, String surname) {
        GamName name = new GamName(firstName, surname);

        assertThat(name.firstName()).isEqualTo(firstName);
        assertThat(name.surname()).isEqualTo(surname);
        assertThat(name.getFullName()).isEqualTo(firstName + " " + surname);
    }

    @ParameterizedTest
    @CsvSource({
            "Jose\u0301, Jos\u00E9",
            "D\u2019Avila, D'Avila",
            "Maria\u2010Clara, Maria-Clara"
    })
    @DisplayName("EP - equivalent Unicode value -> normalized before saving")
    void equivalentUnicodeValueShouldBeNormalized(String rawFirstName, String expectedFirstName) {
        GamName name = new GamName(rawFirstName, "Silva");

        assertThat(name.firstName()).isEqualTo(expectedFirstName);
    }

    @ParameterizedTest
    @MethodSource("firstNamesWithMaximumLength")
    @DisplayName("BVA - firstName length = 32 -> accepted")
    void firstNameLengthAtMaximumShouldBeAccepted(String firstName) {
        GamName name = new GamName(firstName, "Silva");

        assertThat(name.firstName()).isEqualTo(firstName);
    }

    @ParameterizedTest
    @MethodSource("surnamesWithMaximumLength")
    @DisplayName("BVA - surname length = 64 -> accepted")
    void surnameLengthAtMaximumShouldBeAccepted(String surname) {
        GamName name = new GamName("Maria", surname);

        assertThat(name.surname()).isEqualTo(surname);
    }

    @ParameterizedTest
    @MethodSource("firstNamesAboveMaximumLength")
    @DisplayName("BVA - firstName length = 33 -> validation error")
    void firstNameAboveMaximumShouldReturnValidationError(String firstName) {
        assertThatThrownBy(() -> new GamName(firstName, "Silva"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("surnamesAboveMaximumLength")
    @DisplayName("BVA - surname length = 65 -> validation error")
    void surnameAboveMaximumShouldReturnValidationError(String surname) {
        assertThatThrownBy(() -> new GamName("Maria", surname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("EP - blank firstName -> validation error")
    void blankFirstNameShouldReturnValidationError(String firstName) {
        assertThatThrownBy(() -> new GamName(firstName, "Silva"))
                .isInstanceOfAny(IllegalArgumentException.class, NullPointerException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("EP - blank surname -> validation error")
    void blankSurnameShouldReturnValidationError(String surname) {
        assertThatThrownBy(() -> new GamName("Maria", surname))
                .isInstanceOfAny(IllegalArgumentException.class, NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "A-", "A'"})
    @DisplayName("BVA - firstName with fewer than 2 letters -> validation error")
    void firstNameWithFewerThanTwoLettersShouldReturnValidationError(String firstName) {
        assertThatThrownBy(() -> new GamName(firstName, "Silva"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"B", "B-", "B'"})
    @DisplayName("BVA - surname with fewer than 2 letters -> validation error")
    void surnameWithFewerThanTwoLettersShouldReturnValidationError(String surname) {
        assertThatThrownBy(() -> new GamName("Maria", surname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Maria1", "12345", "$%&%@#Y(", "------------", "Ana.", "Ana, Maria", "Ana\uD83D\uDE00"})
    @DisplayName("EP - invalid firstName format -> validation error")
    void invalidFirstNameFormatShouldReturnValidationError(String firstName) {
        assertThatThrownBy(() -> new GamName(firstName, "Silva"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Silva1", "12345", "$%&%@#Y(", "------------", "Silva.", "Silva, Santos", "Silva\uD83D\uDE00"})
    @DisplayName("EP - invalid surname format -> validation error")
    void invalidSurnameFormatShouldReturnValidationError(String surname) {
        assertThatThrownBy(() -> new GamName("Maria", surname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {" Maria", "Maria ", " Maria "})
    @DisplayName("EP - firstName with leading or trailing spaces -> validation error")
    void firstNameWithLeadingOrTrailingSpacesShouldReturnValidationError(String firstName) {
        assertThatThrownBy(() -> new GamName(firstName, "Silva"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {" Silva", "Silva ", " Silva "})
    @DisplayName("EP - surname with leading or trailing spaces -> validation error")
    void surnameWithLeadingOrTrailingSpacesShouldReturnValidationError(String surname) {
        assertThatThrownBy(() -> new GamName("Maria", surname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Maria  Clara", "Maria--Clara", "Maria''Clara", "Maria -Clara", "Maria- Clara"})
    @DisplayName("EP - repeated firstName separators -> validation error")
    void repeatedFirstNameSeparatorsShouldReturnValidationError(String firstName) {
        assertThatThrownBy(() -> new GamName(firstName, "Silva"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Silva  Santos", "Silva--Santos", "Silva''Santos", "Silva -Santos", "Silva- Santos"})
    @DisplayName("EP - repeated surname separators -> validation error")
    void repeatedSurnameSeparatorsShouldReturnValidationError(String surname) {
        assertThatThrownBy(() -> new GamName("Maria", surname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<String> firstNamesWithMaximumLength() {
        return Stream.of("a".repeat(32), "\u00C1".repeat(32));
    }

    private static Stream<String> surnamesWithMaximumLength() {
        return Stream.of("a".repeat(64), "\u00C1".repeat(64));
    }

    private static Stream<String> firstNamesAboveMaximumLength() {
        return Stream.of("a".repeat(33), "\u00C1".repeat(33));
    }

    private static Stream<String> surnamesAboveMaximumLength() {
        return Stream.of("a".repeat(65), "\u00C1".repeat(65));
    }
}
