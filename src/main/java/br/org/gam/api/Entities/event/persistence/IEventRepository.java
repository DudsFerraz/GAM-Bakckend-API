package br.org.gam.api.Entities.event.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface IEventRepository extends JpaRepository<EventEntity, UUID>,
                                          JpaSpecificationExecutor<EventEntity> {
}
