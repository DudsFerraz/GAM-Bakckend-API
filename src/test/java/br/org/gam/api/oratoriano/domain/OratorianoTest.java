package br.org.gam.api.oratoriano.domain;

import br.org.gam.api.shared.domain.GamName;
import br.org.gam.api.shared.phonenumber.GamPhoneNumber;
import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.UnitTest;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@DisplayName("Oratoriano Aggregate")
class OratorianoTest {

    @Nested
    @FunctionalTest
    @DisplayName("Functional")
    class Functional {

        @Test
        @DisplayName("EP - valid registration data -> preserves GamName and GamPhoneNumber")
        void validRegistrationDataShouldPreserveCommonPrimitives() {
            GamName name = new GamName("Ana", "Silva");
            GamPhoneNumber phoneNumber = GamPhoneNumber.fromString("+5519998877665");
            LocalDate birthDate = LocalDate.of(2015, 1, 10);

            Oratoriano oratoriano = Oratoriano.register(name, birthDate, phoneNumber);

            assertThat(oratoriano.getName()).isSameAs(name);
            assertThat(oratoriano.getBirthDate()).isEqualTo(birthDate);
            assertThat(oratoriano.getPhoneNumber()).isSameAs(phoneNumber);
        }
    }
}
