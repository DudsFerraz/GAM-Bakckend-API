package br.org.gam.api.gamLocation.application.useCases;

import br.org.gam.api.gamLocation.application.GamLocationDuplicateLookup;
import br.org.gam.api.gamLocation.application.GamLocationMapper;
import br.org.gam.api.gamLocation.application.GamLocationRDTO;
import br.org.gam.api.gamLocation.persistence.GamLocationEntity;
import br.org.gam.api.gamLocation.persistence.GamLocationRepository;
import br.org.gam.api.shared.activitylog.ActivityEvents;
import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.UnitTest;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Create GamLocation Use Case")
class CreateGamLocationTest {

    @Mock
    private GamLocationRepository gamLocationRepository;

    @Mock
    private GamLocationMapper gamLocationMapper;

    @Mock
    private ActivityEvents activityEvents;

    @Mock
    private GamLocationDuplicateLookup duplicateLookup;

    @InjectMocks
    private CreateGamLocation createGamLocation;

    @Nested
    @FunctionalTest
    @DisplayName("Functional")
    class Functional {

        @Test
        @DisplayName("EP - valid GamLocation data -> GamLocation is created")
        void validGamLocationDataShouldCreateGamLocation() {
            BigDecimal latitude = new BigDecimal("-22.90684670");
            BigDecimal longitude = new BigDecimal("-47.06158810");
            GamLocationMutationDTO dto = new GamLocationMutationDTO(
                    "  Parish Hall  ",
                    null,
                    "  Campinas  ",
                    "  SP  ",
                    null,
                    "  BR  ",
                    latitude,
                    longitude
            );
            UUID responseId = UUID.randomUUID();
            GamLocationRDTO expectedResponse = new GamLocationRDTO(
                    responseId, "Parish Hall", null, "Campinas", "SP", null, "BR", latitude, longitude
            );

            when(gamLocationRepository.findActiveDuplicate(
                    "parish hall", "", "campinas", "sp", "", "br"
            )).thenReturn(Optional.empty());
            when(gamLocationRepository.saveAndFlush(any(GamLocationEntity.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));
            when(gamLocationMapper.entityToRDTO(any(GamLocationEntity.class))).thenReturn(expectedResponse);

            GamLocationRDTO response = createGamLocation.create(dto);

            assertThat(response).isSameAs(expectedResponse);

            ArgumentCaptor<GamLocationEntity> gamLocationCaptor = ArgumentCaptor.forClass(GamLocationEntity.class);
            verify(gamLocationRepository).saveAndFlush(gamLocationCaptor.capture());
            GamLocationEntity gamLocation = gamLocationCaptor.getValue();

            assertThat(gamLocation.getId()).isNotNull();
            assertThat(gamLocation.getId().version()).isEqualTo(7);
            assertThat(gamLocation.getName()).isEqualTo("Parish Hall");
            assertThat(gamLocation.getStreet()).isNull();
            assertThat(gamLocation.getCity()).isEqualTo("Campinas");
            assertThat(gamLocation.getState()).isEqualTo("SP");
            assertThat(gamLocation.getPostalCode()).isNull();
            assertThat(gamLocation.getCountryCode()).isEqualTo("BR");
            assertThat(gamLocation.getLatitude()).isSameAs(latitude);
            assertThat(gamLocation.getLongitude()).isSameAs(longitude);
            assertThat(gamLocation.getIdentityName()).isEqualTo("parish hall");
            assertThat(gamLocation.getIdentityStreet()).isEmpty();
            assertThat(gamLocation.getIdentityCity()).isEqualTo("campinas");
            assertThat(gamLocation.getIdentityState()).isEqualTo("sp");
            assertThat(gamLocation.getIdentityPostalCode()).isEmpty();
            assertThat(gamLocation.getIdentityCountryCode()).isEqualTo("br");
            verify(activityEvents).gamLocationCreated(gamLocation.getId());
        }
    }
}
