package br.org.gam.api.Entities.presence.services;

import br.org.gam.api.Entities.events.generic.Event;
import br.org.gam.api.Entities.events.generic.exception.EventNotFoundException;
import br.org.gam.api.Entities.events.generic.services.getEventInstance.GetEventInstance;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.exception.MemberNotFoundException;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.PresenceMapper;
import br.org.gam.api.Entities.presence.exception.PresenceConflictException;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.persistence.PresenceRepository;
import br.org.gam.api.Entities.presence.services.registerPresence.RegisterPresenceDTO;
import br.org.gam.api.Entities.presence.services.registerPresence.RegisterPresenceRDTO;
import br.org.gam.api.Entities.presence.services.registerPresence.SpringRegisterPresence;
import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@UnitTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Register Presence Use Case")
class SpringRegisterPresenceTest {

    @Mock
    private PresenceRepository presenceRepo;

    @Mock
    private PresenceMapper presenceMapper;

    @Mock
    private GetMemberInstance getMemberInstance;

    @Mock
    private GetEventInstance getEventInstance;

    @InjectMocks
    private SpringRegisterPresence registerPresence;

    @Nested
    @FunctionalTest
    @DisplayName("Functional")
    class Functional {

        @Test
        @DisplayName("EP - unregistered member in event -> presence is registered")
        void unregisteredMemberInEventShouldRegisterPresence() {
            UUID memberId = UUID.randomUUID();
            UUID eventId = UUID.randomUUID();
            RegisterPresenceDTO dto = new RegisterPresenceDTO(eventId, memberId, "  Present at entrance  ");
            Member member = mock(Member.class);
            Event event = mock(Event.class);
            PresenceEntity mappedEntity = new PresenceEntity();
            PresenceEntity savedEntity = new PresenceEntity();
            RegisterPresenceRDTO expectedResponse = new RegisterPresenceRDTO(UUID.randomUUID());

            when(presenceRepo.existsByMember_IdAndEvent_Id(memberId, eventId)).thenReturn(false);
            when(getMemberInstance.domainById(memberId)).thenReturn(member);
            when(getEventInstance.domainById(eventId)).thenReturn(event);
            when(presenceMapper.domainToEntity(any(Presence.class))).thenReturn(mappedEntity);
            when(presenceRepo.save(mappedEntity)).thenReturn(savedEntity);
            when(presenceMapper.entityToRegisterPresenceRDTO(savedEntity)).thenReturn(expectedResponse);

            RegisterPresenceRDTO response = registerPresence.register(dto);

            assertThat(response).isSameAs(expectedResponse);

            ArgumentCaptor<Presence> presenceCaptor = ArgumentCaptor.forClass(Presence.class);
            verify(presenceMapper).domainToEntity(presenceCaptor.capture());
            Presence presence = presenceCaptor.getValue();

            assertThat(presence.getId()).isNotNull();
            assertThat(presence.getId().version()).isEqualTo(7);
            assertThat(presence.getMember()).isSameAs(member);
            assertThat(presence.getEvent()).isSameAs(event);
            assertThat(presence.getObservations()).isEqualTo("Present at entrance");
            verify(presenceRepo).save(mappedEntity);
        }

        @Test
        @DisplayName("EP - already registered member in event -> conflict error")
        void alreadyRegisteredMemberInEventShouldReturnConflictError() {
            UUID memberId = UUID.randomUUID();
            UUID eventId = UUID.randomUUID();
            RegisterPresenceDTO dto = new RegisterPresenceDTO(eventId, memberId, null);

            when(presenceRepo.existsByMember_IdAndEvent_Id(memberId, eventId)).thenReturn(true);

            assertThatThrownBy(() -> registerPresence.register(dto))
                    .isInstanceOf(PresenceConflictException.class)
                    .hasMessage("Presence already registered");

            verifyNoInteractions(getMemberInstance, getEventInstance, presenceMapper);
            verify(presenceRepo, never()).save(any());
        }

        @Test
        @DisplayName("EP - missing member id -> not found error")
        void missingMemberIdShouldReturnNotFoundError() {
            UUID memberId = UUID.randomUUID();
            UUID eventId = UUID.randomUUID();
            RegisterPresenceDTO dto = new RegisterPresenceDTO(eventId, memberId, null);

            when(presenceRepo.existsByMember_IdAndEvent_Id(memberId, eventId)).thenReturn(false);
            when(getMemberInstance.domainById(memberId))
                    .thenThrow(new MemberNotFoundException("Could not find member with id " + memberId));

            assertThatThrownBy(() -> registerPresence.register(dto))
                    .isInstanceOf(MemberNotFoundException.class)
                    .hasMessage("Could not find member with id " + memberId);

            verifyNoInteractions(getEventInstance, presenceMapper);
            verify(presenceRepo, never()).save(any());
        }

        @Test
        @DisplayName("EP - missing event id -> not found error")
        void missingEventIdShouldReturnNotFoundError() {
            UUID memberId = UUID.randomUUID();
            UUID eventId = UUID.randomUUID();
            RegisterPresenceDTO dto = new RegisterPresenceDTO(eventId, memberId, null);
            Member member = mock(Member.class);

            when(presenceRepo.existsByMember_IdAndEvent_Id(memberId, eventId)).thenReturn(false);
            when(getMemberInstance.domainById(memberId)).thenReturn(member);
            when(getEventInstance.domainById(eventId))
                    .thenThrow(new EventNotFoundException("Could not find event with id " + eventId));

            assertThatThrownBy(() -> registerPresence.register(dto))
                    .isInstanceOf(EventNotFoundException.class)
                    .hasMessage("Could not find event with id " + eventId);

            verifyNoInteractions(presenceMapper);
            verify(presenceRepo, never()).save(any());
        }
    }
}
