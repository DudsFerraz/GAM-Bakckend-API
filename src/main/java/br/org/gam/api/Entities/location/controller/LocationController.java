package br.org.gam.api.Entities.location.controller;

import br.org.gam.api.Entities.location.services.createLocation.CreateLocationDTO;
import br.org.gam.api.Entities.location.services.createLocation.CreateLocationRDTO;
import br.org.gam.api.Entities.location.services.createLocation.CreateLocation;
import br.org.gam.api.Entities.location.services.getLocation.GetLocationRDTO;
import br.org.gam.api.Entities.location.services.getLocation.GetLocation;
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

    private final CreateLocation createLocation;
    private final GetLocation getLocation;

    public LocationController(CreateLocation createLocation, GetLocation getLocation) {
        this.createLocation = createLocation;
        this.getLocation = getLocation;
    }

    @PostMapping
    public ResponseEntity<CreateLocationRDTO> createLocation(@RequestBody @Valid CreateLocationDTO dto) {
        CreateLocationRDTO responseDTO = createLocation.create(dto);

        URI httpLocation  = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(httpLocation).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetLocationRDTO> getLocationById(@PathVariable UUID id) {
        GetLocationRDTO responseDTO = getLocation.byId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<GetLocationRDTO>> getAllLocations(Pageable pageable) {
        return ResponseEntity.ok(
                getLocation.all(pageable)
        );
    }
}
