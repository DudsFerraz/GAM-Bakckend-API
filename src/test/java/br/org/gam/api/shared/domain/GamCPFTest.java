package br.org.gam.api.shared.domain;

import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.UnitTest;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@UnitTest
@FunctionalTest
@DisplayName("Functional - GamCPF")
class GamCPFTest {

    @ParameterizedTest
    @CsvSource({
            "52998224725, 52998224725",
            "529.982.247-25, 52998224725",
            "' 52998224725 ', 52998224725",
            "' 529.982.247-25 ', 52998224725"
    })
    @DisplayName("EP - accepted CPF form -> canonical eleven-digit value")
    void acceptedCpfFormShouldProduceCanonicalValue(String rawCpf, String expectedCpf) {
        GamCPF cpf = new GamCPF(rawCpf);

        assertThat(cpf.value()).isEqualTo(expectedCpf);
    }

    @Test
    @DisplayName("EP - formatted and unformatted CPF -> equal primitive values")
    void formattedAndUnformattedCpfShouldProduceEqualPrimitiveValues() {
        GamCPF unformatted = new GamCPF("52998224725");
        GamCPF formatted = new GamCPF("529.982.247-25");

        assertThat(formatted).isEqualTo(unformatted);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("EP - absent CPF -> validation error")
    void absentCpfShouldReturnValidationError(String rawCpf) {
        assertCreationFails(() -> new GamCPF(rawCpf));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "529.982247-25",
            "529 982 247 25",
            "529/982/247-25",
            "529.982.247.25",
            "5299822472A",
            "5299822472９",
            "5299822472",
            "529982247250"
    })
    @DisplayName("EP - malformed CPF syntax -> validation error")
    void malformedCpfSyntaxShouldReturnValidationError(String rawCpf) {
        assertCreationFails(() -> new GamCPF(rawCpf));
    }

    @Test
    @DisplayName("EP - incorrect first CPF check digit -> validation error")
    void incorrectFirstCheckDigitShouldReturnValidationError() {
        assertCreationFails(() -> new GamCPF("52998224715"));
    }

    @Test
    @DisplayName("EP - incorrect second CPF check digit -> validation error")
    void incorrectSecondCheckDigitShouldReturnValidationError() {
        assertCreationFails(() -> new GamCPF("52998224724"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"00000000000", "111.111.111-11", "99999999999"})
    @DisplayName("EP - all-identical CPF digits -> validation error")
    void allIdenticalCpfDigitsShouldReturnValidationError(String rawCpf) {
        assertCreationFails(() -> new GamCPF(rawCpf));
    }

    private static void assertCreationFails(ThrowingCallable callable) {
        assertThatThrownBy(callable)
                .isInstanceOfAny(IllegalArgumentException.class, NullPointerException.class);
    }
}
