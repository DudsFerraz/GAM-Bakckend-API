package br.org.gam.api.shared.domain;

import br.org.gam.api.testing.annotation.StructuralTest;
import br.org.gam.api.testing.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@StructuralTest
@DisplayName("Structural - GamCPF")
class GamCPFStructuralTest {

    @Test
    @DisplayName("Condition - first remainder below 2 -> first check digit is zero")
    void firstRemainderBelowTwoShouldProduceZeroCheckDigit() {
        GamCPF cpf = new GamCPF("12345678909");

        assertThat(cpf.value()).isEqualTo("12345678909");
    }

    @Test
    @DisplayName("Condition - second remainder below 2 -> second check digit is zero")
    void secondRemainderBelowTwoShouldProduceZeroCheckDigit() {
        GamCPF cpf = new GamCPF("12345678810");

        assertThat(cpf.value()).isEqualTo("12345678810");
    }
}
