package br.org.gam.api.event.persistence;

import br.org.gam.api.gamLocation.persistence.GamLocationEntity;
import br.org.gam.api.testing.annotation.StructuralTest;
import br.org.gam.api.testing.annotation.UnitTest;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@StructuralTest
@DisplayName("Persistence structure - Event GamLocation relationship")
class EventGamLocationPersistenceStructuralTest {

    @Test
    @DisplayName("REQ-EVENT-004 - Event relationship -> required GamLocation in JPA mapping")
    void eventGamLocationRelationshipShouldBeRequired() {
        Field gamLocation = Arrays.stream(EventEntity.class.getDeclaredFields())
                .filter(field -> field.getType().equals(GamLocationEntity.class))
                .findFirst()
                .orElseThrow();

        ManyToOne relationship = gamLocation.getAnnotation(ManyToOne.class);
        JoinColumn joinColumn = gamLocation.getAnnotation(JoinColumn.class);

        assertThat(relationship).isNotNull();
        assertThat(relationship.optional()).isFalse();
        assertThat(joinColumn).isNotNull();
        assertThat(joinColumn.nullable()).isFalse();
    }
}
