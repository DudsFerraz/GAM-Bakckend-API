package br.org.gam.api.Entities.location.services.getLocation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GetLocation {
    GetLocationRDTO byId(UUID id);
    Page<GetLocationRDTO> all(Pageable pageable);
}
