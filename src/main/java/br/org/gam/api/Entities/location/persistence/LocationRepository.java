package br.org.gam.api.Entities.location.persistence;

import br.org.gam.api.common.persistence.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends BaseRepository<LocationEntity, UUID> {
}
