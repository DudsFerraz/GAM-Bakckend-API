package br.org.gam.api.Entities.location.controller;

import br.org.gam.api.Entities.location.services.createLocation.CreateLocationDTO;
import br.org.gam.api.Entities.location.services.createLocation.CreateLocationRDTO;
import br.org.gam.api.Entities.location.services.createLocation.CreateLocation;
import br.org.gam.api.Entities.location.services.getAllLocations.GetAllLocations;
import br.org.gam.api.Entities.location.services.getLocationById.GetLocationByIdDTO;
import br.org.gam.api.Entities.location.services.getLocationById.GetLocationById;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final CreateLocation createLocationService;
    private final GetLocationById getLocationByIdService;
    private final GetAllLocations getAllLocationsService;

    public LocationController(CreateLocation createLocationService, GetAllLocations getAllLocationsService, GetLocationById getLocationByIdService) {
        this.createLocationService = createLocationService;
        this.getAllLocationsService = getAllLocationsService;
        this.getLocationByIdService = getLocationByIdService;
    }


    @PostMapping
    public ResponseEntity<CreateLocationRDTO> createLocation(@RequestBody @Valid CreateLocationDTO dto) {
        CreateLocationRDTO responseDTO = createLocationService.createLocation(dto);

        URI httpLocation  = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(httpLocation).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetLocationByIdDTO> getLocationById(@PathVariable UUID id) {
        GetLocationByIdDTO responseDTO = getLocationByIdService.getLocationById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<GetLocationByIdDTO>> getAllLocations(Pageable pageable) {
        return ResponseEntity.ok(
                getAllLocationsService.getAllLocations(pageable)
        );
    }
}
