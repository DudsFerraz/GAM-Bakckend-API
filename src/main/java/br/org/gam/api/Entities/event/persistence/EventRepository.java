package br.org.gam.api.Entities.event.persistence;

import br.org.gam.api.common.persistence.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EventRepository extends BaseRepository<EventEntity, UUID>,
                                          JpaSpecificationExecutor<EventEntity> {
}
