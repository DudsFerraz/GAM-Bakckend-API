package br.org.gam.api.gamLocation.application.useCases;

import br.org.gam.api.gamLocation.application.GamLocationEntityLoader;
import br.org.gam.api.gamLocation.application.GamLocationMapper;
import br.org.gam.api.gamLocation.application.GamLocationRDTO;
import br.org.gam.api.gamLocation.persistence.GamLocationEntity;
import br.org.gam.api.gamLocation.persistence.GamLocationRepository;
import br.org.gam.api.shared.exception.NotFoundException;
import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.StructuralTest;
import br.org.gam.api.testing.annotation.UnitTest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@UnitTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Get GamLocations Use Case")
class GetGamLocationsTest {

    @Mock
    private GamLocationEntityLoader gamLocationEntityLoader;

    @Mock
    private GamLocationMapper gamLocationMapper;

    @Mock
    private GamLocationRepository gamLocationRepository;

    @InjectMocks
    private GetGamLocations getGamLocations;

    @Nested
    @FunctionalTest
    @DisplayName("Functional")
    class Functional {

        @Test
        @DisplayName("EP - existing GamLocation id -> GamLocation response")
        void existingGamLocationIdShouldReturnGamLocationResponse() {
            UUID id = UUID.randomUUID();
            GamLocationEntity entity = new GamLocationEntity();
            GamLocationRDTO expectedResponse = response(id, "Parish Hall");

            when(gamLocationEntityLoader.requiredById(id)).thenReturn(entity);
            when(gamLocationMapper.entityToRDTO(entity)).thenReturn(expectedResponse);

            GamLocationRDTO response = getGamLocations.byId(id);

            assertThat(response).isSameAs(expectedResponse);
            verify(gamLocationEntityLoader).requiredById(id);
            verify(gamLocationMapper).entityToRDTO(entity);
        }

        @Test
        @DisplayName("EP - missing GamLocation id -> not found error")
        void missingGamLocationIdShouldReturnNotFoundError() {
            UUID id = UUID.randomUUID();

            when(gamLocationEntityLoader.requiredById(id))
                    .thenThrow(NotFoundException.resource("GamLocation", id));

            assertThatThrownBy(() -> getGamLocations.byId(id))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("GamLocation not found with identifier " + id);

            verifyNoInteractions(gamLocationMapper, gamLocationRepository);
        }

        @Test
        @DisplayName("EP - pageable GamLocations -> mapped GamLocation page")
        void pageableGamLocationsShouldReturnMappedGamLocationPage() {
            Pageable pageable = PageRequest.of(0, 10);
            GamLocationEntity firstEntity = new GamLocationEntity();
            GamLocationEntity secondEntity = new GamLocationEntity();
            GamLocationRDTO firstResponse = response(UUID.randomUUID(), "Parish Hall");
            GamLocationRDTO secondResponse = response(UUID.randomUUID(), "Main Church");

            when(gamLocationRepository.findAll(pageable))
                    .thenReturn(new PageImpl<>(List.of(firstEntity, secondEntity), pageable, 2));
            when(gamLocationMapper.entityToRDTO(firstEntity)).thenReturn(firstResponse);
            when(gamLocationMapper.entityToRDTO(secondEntity)).thenReturn(secondResponse);

            Page<GamLocationRDTO> response = getGamLocations.all(pageable);

            assertThat(response.getContent()).containsExactly(firstResponse, secondResponse);
            assertThat(response.getTotalElements()).isEqualTo(2);
            verify(gamLocationRepository).findAll(pageable);
            verify(gamLocationMapper).entityToRDTO(firstEntity);
            verify(gamLocationMapper).entityToRDTO(secondEntity);
        }
    }

    @Nested
    @StructuralTest
    @DisplayName("Structural")
    class Structural {

        @Test
        @DisplayName("empty page -> empty mapped page")
        void emptyPageShouldReturnEmptyMappedPage() {
            Pageable pageable = PageRequest.of(0, 10);

            when(gamLocationRepository.findAll(pageable)).thenReturn(Page.empty(pageable));

            Page<GamLocationRDTO> response = getGamLocations.all(pageable);

            assertThat(response.getContent()).isEmpty();
            assertThat(response.getTotalElements()).isZero();
            verify(gamLocationRepository).findAll(pageable);
            verifyNoInteractions(gamLocationMapper);
        }
    }

    private static GamLocationRDTO response(UUID id, String name) {
        return new GamLocationRDTO(
                id,
                name,
                null,
                "Campinas",
                "SP",
                null,
                "BR",
                new BigDecimal("-22.90684670"),
                new BigDecimal("-47.06158810")
        );
    }
}
