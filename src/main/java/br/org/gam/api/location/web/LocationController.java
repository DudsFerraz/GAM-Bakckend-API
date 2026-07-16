package br.org.gam.api.location.web;

import br.org.gam.api.location.application.LocationRDTO;
import br.org.gam.api.location.application.useCases.createLocation.CreateLocation;
import br.org.gam.api.location.application.useCases.createLocation.CreateLocationDTO;
import br.org.gam.api.location.application.useCases.createLocation.CreateLocationRDTO;
import br.org.gam.api.location.application.useCases.GetLocation;
import br.org.gam.api.shared.web.PagedResponse;
import br.org.gam.api.shared.web.PublicApiUri;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final CreateLocation createLocation;
    private final GetLocation getLocation;

    public LocationController(CreateLocation createLocation, GetLocation getLocation) {
        this.createLocation = createLocation;
        this.getLocation = getLocation;
    }

    @Operation(operationId = "createLocation")
    @PostMapping
    public ResponseEntity<CreateLocationRDTO> createLocation(@RequestBody @Valid CreateLocationDTO dto) {
        CreateLocationRDTO responseDTO = createLocation.create(dto);

        return ResponseEntity.created(PublicApiUri.forResource("/locations/" + responseDTO.id()))
                .body(responseDTO);
    }

    @Operation(operationId = "getLocation")
    @GetMapping("/{id}")
    public ResponseEntity<LocationRDTO> getLocationById(@PathVariable UUID id) {
        LocationRDTO responseDTO = getLocation.byId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(operationId = "getLocations")
    @GetMapping
    public ResponseEntity<PagedResponse<LocationRDTO>> getAllLocations(Pageable pageable) {
        return ResponseEntity.ok(PagedResponse.from(getLocation.all(pageable)));
    }
}
