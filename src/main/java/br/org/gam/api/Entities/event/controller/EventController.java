package br.org.gam.api.Entities.event.controller;

import br.org.gam.api.Entities.event.services.createEvent.CreateEventDTO;
import br.org.gam.api.Entities.event.services.createEvent.CreateEventRDTO;
import br.org.gam.api.Entities.event.services.createEvent.CreateEvent;
import br.org.gam.api.Entities.event.services.getEvent.GetEventRDTO;
import br.org.gam.api.Entities.event.services.getEvent.GetEvent;
import br.org.gam.api.Entities.event.services.searchEvents.SearchEvents;
import br.org.gam.api.Entities.presence.services.getPresence.GetPresence;
import br.org.gam.api.Entities.presence.services.getPresence.GetPresenceRDTO;
import br.org.gam.api.common.specification.SearchDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {
    private final CreateEvent createEventService;
    private final GetEvent getEventService;
    private final SearchEvents searchEventService;
    private final SpecificationFilterConverter specificationFilterConverter;
    private final GetPresence getPresence;

    public EventController(CreateEvent createEventService,
                           GetEvent getEventService,
                           SearchEvents searchEventService,
                           @Qualifier("eventSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter,
                           GetPresence getPresence) {

        this.createEventService = createEventService;
        this.getEventService = getEventService;
        this.searchEventService = searchEventService;
        this.specificationFilterConverter = specificationFilterConverter;
        this.getPresence = getPresence;
    }

    @PostMapping
    public ResponseEntity<CreateEventRDTO> createEvent(@RequestBody @Valid CreateEventDTO dto){

        CreateEventRDTO responseDTO = createEventService.create(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetEventRDTO> getEventById(@PathVariable UUID id){

        GetEventRDTO responseDTO = getEventService.byId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<GetEventRDTO>> searchEvents(@RequestBody @Valid SearchDTO searchDTO,
                                                           Pageable pageable){

        List<SpecificationFilter> filters = specificationFilterConverter.convert(searchDTO.filters());

        return ResponseEntity.ok(
                searchEventService.search(filters, pageable)
        );
    }

    @GetMapping("/{eventId}/presences")
    public ResponseEntity<Page<GetPresenceRDTO>> getEventPresences(@PathVariable UUID eventId,
                                                                   Pageable pageable){

        return ResponseEntity.ok(
                getPresence.allByEvent(eventId, pageable)
        );
    }
}
