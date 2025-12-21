package br.org.gam.api.Entities.event.controller;

import br.org.gam.api.Entities.RBAC.permission.PermissionEnum;
import br.org.gam.api.Entities.event.services.createEvent.CreateEventDTO;
import br.org.gam.api.Entities.event.services.createEvent.CreateEventRDTO;
import br.org.gam.api.Entities.event.services.createEvent.CreateEvent;
import br.org.gam.api.Entities.event.services.EventRDTO;
import br.org.gam.api.Entities.event.services.getEvent.GetEvent;
import br.org.gam.api.Entities.event.services.searchEvents.SearchEvents;
import br.org.gam.api.Entities.presence.services.getPresence.GetPresence;
import br.org.gam.api.Entities.presence.services.PresenceRDTO;
import br.org.gam.api.common.specification.SearchDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {
    private final CreateEvent createEvent;
    private final GetEvent getEvent;
    private final SearchEvents searchEvent;
    private final SpecificationFilterConverter specificationFilterConverter;
    private final GetPresence getPresence;

    public EventController(CreateEvent createEvent,
                           GetEvent getEvent,
                           SearchEvents searchEvent,
                           @Qualifier("eventSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter,
                           GetPresence getPresence) {

        this.createEvent = createEvent;
        this.getEvent = getEvent;
        this.searchEvent = searchEvent;
        this.specificationFilterConverter = specificationFilterConverter;
        this.getPresence = getPresence;
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.EVENT_CREATE + "')")
    @PostMapping
    public ResponseEntity<CreateEventRDTO> createEvent(@RequestBody @Valid CreateEventDTO dto){

        CreateEventRDTO responseDTO = createEvent.create(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventRDTO> getEventById(@PathVariable UUID id){

        EventRDTO responseDTO = getEvent.byId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.EVENT_SEARCH + "')")
    @PostMapping("/search")
    public ResponseEntity<Page<EventRDTO>> searchEvents(@RequestBody @Valid SearchDTO searchDTO,
                                                        Pageable pageable){

        List<SpecificationFilter> filters = specificationFilterConverter.convert(searchDTO.filters());

        return ResponseEntity.ok(
                searchEvent.search(filters, pageable)
        );
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.EVENT_GET_PRESENCES + "')")
    @GetMapping("/{eventId}/presences")
    public ResponseEntity<Page<PresenceRDTO>> getEventPresences(@PathVariable UUID eventId,
                                                                Pageable pageable){

        return ResponseEntity.ok(
                getPresence.allByEvent(eventId, pageable)
        );
    }
}
