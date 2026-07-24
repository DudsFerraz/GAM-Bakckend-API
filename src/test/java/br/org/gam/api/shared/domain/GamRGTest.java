package br.org.gam.api.shared.domain;

import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.UnitTest;
import java.util.stream.Stream;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@FunctionalTest
@DisplayName("Functional - GamRG")
class GamRGTest {

    @ParameterizedTest
    @CsvSource({
            "' 12.345.678-X ', 12.345.678-X",
            "' abC  12-x ', 'abC  12-x'",
            "12/345678-9, 12/345678-9"
    })
    @DisplayName("EP - accepted RG -> surrounding spaces trimmed and representation preserved")
    void acceptedRgShouldTrimSurroundingSpacesAndPreserveRepresentation(String rawRg, String expectedRg) {
        GamRG rg = new GamRG(rawRg);

        assertThat(rg.value()).isEqualTo(expectedRg);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "9", "12.345.678-X", "12 345 678 X", "12/345678-9"})
    @DisplayName("EP - bounded RG using allowed characters -> accepted")
    void boundedRgUsingAllowedCharactersShouldBeAccepted(String rawRg) {
        GamRG rg = new GamRG(rawRg);

        assertThat(rg.value()).isEqualTo(rawRg);
    }

    @ParameterizedTest
    @MethodSource("rgValuesAtLengthBoundaries")
    @DisplayName("BVA - RG length = 1 or 20 after trimming -> accepted")
    void rgAtLengthBoundaryShouldBeAccepted(String rawRg, String expectedRg) {
        GamRG rg = new GamRG(rawRg);

        assertThat(rg.value()).isEqualTo(expectedRg);
    }

    @Test
    @DisplayName("BVA - RG length = 21 after trimming -> validation error")
    void rgAboveMaximumLengthShouldReturnValidationError() {
        assertCreationFails(() -> new GamRG("A".repeat(21)));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("EP - absent RG -> validation error")
    void absentRgShouldReturnValidationError(String rawRg) {
        assertCreationFails(() -> new GamRG(rawRg));
    }

    @ParameterizedTest
    @ValueSource(strings = {"---", "...", " / - / "})
    @DisplayName("EP - RG without an ASCII letter or digit -> validation error")
    void rgWithoutAsciiLetterOrDigitShouldReturnValidationError(String rawRg) {
        assertCreationFails(() -> new GamRG(rawRg));
    }

    @ParameterizedTest
    @MethodSource("rgValuesWithDisallowedCharacters")
    @DisplayName("EP - RG with disallowed character -> validation error")
    void rgWithDisallowedCharacterShouldReturnValidationError(String rawRg) {
        assertCreationFails(() -> new GamRG(rawRg));
    }

    @Test
    @DisplayName("EP - RG with no state-specific checksum -> accepted")
    void rgWithoutStateSpecificChecksumShouldBeAccepted() {
        GamRG rg = new GamRG("12.345.678-X");

        assertThat(rg.value()).isEqualTo("12.345.678-X");
    }

    @Test
    @DisplayName("EP - eleven-digit numeric legacy RG -> accepted without inferring CIN semantics")
    void elevenDigitNumericLegacyRgShouldBeAcceptedWithoutInferringCinSemantics() {
        GamRG rg = new GamRG("52998224725");

        assertThat(rg.value()).isEqualTo("52998224725");
    }

    private static void assertCreationFails(ThrowingCallable callable) {
        assertThatThrownBy(callable)
                .isInstanceOfAny(IllegalArgumentException.class, NullPointerException.class);
    }

    private static Stream<Arguments> rgValuesAtLengthBoundaries() {
        return Stream.of(
                Arguments.of("A", "A"),
                Arguments.of(" " + "A".repeat(20) + " ", "A".repeat(20))
        );
    }

    private static Stream<String> rgValuesWithDisallowedCharacters() {
        return Stream.of(
                "12_345",
                "12\\345",
                "12:345",
                "１２３４５",
                "12😀",
                "12\t345",
                "12\n345",
                "12.345.678-9\n",
                "12" + (char) 1 + "345"
        );
    }
}
