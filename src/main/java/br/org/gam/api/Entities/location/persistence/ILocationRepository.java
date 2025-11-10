package br.org.gam.api.Entities.location.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ILocationRepository extends JpaRepository<LocationEntity, UUID> {
}
