package br.org.gam.api.Entities.events.oratorio.services.getOratorioInstance;

import br.org.gam.api.Entities.events.oratorio.Oratorio;
import br.org.gam.api.Entities.events.oratorio.persistence.OratorioEntity;

import java.util.UUID;

public interface GetOratorioInstance {
    Oratorio domainById(UUID id);
    OratorioEntity entityById(UUID id);
}
