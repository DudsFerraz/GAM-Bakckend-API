package br.org.gam.api.Entities.event.controller;

import br.org.gam.api.Entities.event.services.createEvent.CreateEventDTO;
import br.org.gam.api.Entities.event.services.createEvent.CreateEventRDTO;
import br.org.gam.api.Entities.event.services.createEvent.CreateEvent;
import br.org.gam.api.Entities.event.services.getEventById.GetEventByIdDTO;
import br.org.gam.api.Entities.event.services.getEventById.GetEventById;
import br.org.gam.api.Entities.event.services.searchEvents.SearchEvents;
import br.org.gam.api.Entities.presence.services.getPresenceById.GetPresenceByIdDTO;
import br.org.gam.api.Entities.presence.services.getPresencesByEvent.GetPresencesByEvent;
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
    private final GetEventById getEventByIdService;
    private final SearchEvents searchEventService;
    private final SpecificationFilterConverter specificationFilterConverter;
    private final GetPresencesByEvent getPresencesByEventService;

    public EventController(CreateEvent createEventService,
                           GetEventById getEventByIdService,
                           SearchEvents searchEventService,
                           @Qualifier("eventSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter, GetPresencesByEvent getPresencesByEventService) {

        this.createEventService = createEventService;
        this.getEventByIdService = getEventByIdService;
        this.searchEventService = searchEventService;
        this.specificationFilterConverter = specificationFilterConverter;
        this.getPresencesByEventService = getPresencesByEventService;
    }

    @PostMapping
    public ResponseEntity<CreateEventRDTO> createEvent(@RequestBody @Valid CreateEventDTO dto){

        CreateEventRDTO responseDTO = createEventService.createEvent(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetEventByIdDTO> getEventById(@PathVariable UUID id){

        GetEventByIdDTO responseDTO = getEventByIdService.getEventById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<GetEventByIdDTO>> searchEvents(@RequestBody @Valid SearchDTO searchDTO,
                                                              Pageable pageable){

        List<SpecificationFilter> filters = specificationFilterConverter.convert(searchDTO.filters());

        return ResponseEntity.ok(
                searchEventService.searchEvents(filters, pageable)
        );
    }

    @GetMapping("/{eventId}/presences")
    public ResponseEntity<Page<GetPresenceByIdDTO>> getEventPresences(@PathVariable UUID eventId,
                                                                      Pageable pageable){

        return ResponseEntity.ok(
                getPresencesByEventService.getEventPresences(eventId, pageable)
        );
    }
}
