package br.org.gam.api.event.oratorio.persistence;

import br.org.gam.api.event.oratorio.domain.Oratorio;
import br.org.gam.api.shared.persistence.BaseRepository;
import java.util.UUID;

public interface OratorioRepository extends BaseRepository<OratorioEntity, UUID> {
}
