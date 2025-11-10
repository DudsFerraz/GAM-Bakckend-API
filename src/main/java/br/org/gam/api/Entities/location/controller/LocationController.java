package br.org.gam.api.Entities.location.controller;

import br.org.gam.api.Entities.location.services.createLocation.CreateLocationDTO;
import br.org.gam.api.Entities.location.services.createLocation.CreateLocationResponseDTO;
import br.org.gam.api.Entities.location.services.createLocation.ICreateLocationService;
import br.org.gam.api.Entities.location.services.getAllLocations.IGetAllLocationsService;
import br.org.gam.api.Entities.location.services.getLocationById.GetLocationByIdDTO;
import br.org.gam.api.Entities.location.services.getLocationById.IGetLocationByIdService;
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

    private final ICreateLocationService createLocationService;
    private final IGetLocationByIdService getLocationByIdService;
    private final IGetAllLocationsService getAllLocationsService;

    public LocationController(ICreateLocationService createLocationService, IGetAllLocationsService getAllLocationsService, IGetLocationByIdService getLocationByIdService) {
        this.createLocationService = createLocationService;
        this.getAllLocationsService = getAllLocationsService;
        this.getLocationByIdService = getLocationByIdService;
    }


    @PostMapping
    public ResponseEntity<CreateLocationResponseDTO> createLocation(@RequestBody @Valid CreateLocationDTO dto) {
        CreateLocationResponseDTO responseDTO = createLocationService.createLocation(dto);

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
