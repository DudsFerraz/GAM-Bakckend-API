package br.org.gam.api.Entities.event.controller;

import br.org.gam.api.Entities.event.services.createEvent.CreateEventDTO;
import br.org.gam.api.Entities.event.services.createEvent.CreateEventResponseDTO;
import br.org.gam.api.Entities.event.services.createEvent.ICreateEventService;
import br.org.gam.api.Entities.event.services.getEventById.GetEventByIdDTO;
import br.org.gam.api.Entities.event.services.getEventById.IGetEventByIdService;
import br.org.gam.api.Entities.event.services.searchEvents.ISearchEventsService;
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
    private final ICreateEventService createEventService;
    private final IGetEventByIdService getEventByIdService;
    private final ISearchEventsService searchEventService;
    private final SpecificationFilterConverter specificationFilterConverter;

    public EventController(ICreateEventService createEventService,
                           IGetEventByIdService getEventByIdService,
                           ISearchEventsService searchEventService,
                           @Qualifier("eventSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter) {

        this.createEventService = createEventService;
        this.getEventByIdService = getEventByIdService;
        this.searchEventService = searchEventService;
        this.specificationFilterConverter = specificationFilterConverter;
    }

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(@RequestBody @Valid CreateEventDTO dto){

        CreateEventResponseDTO responseDTO = createEventService.createEvent(dto);

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
}
