package br.org.gam.api.Entities.events.oratorio.services.getOratorio;

import br.org.gam.api.Entities.events.oratorio.services.OratorioRDTO;

import java.util.UUID;

public interface GetOratorio {
    OratorioRDTO byId(UUID id);
}
