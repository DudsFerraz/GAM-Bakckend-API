package br.org.gam.api.Entities.oratoriano.services.getOratorianoInstance;

import br.org.gam.api.Entities.oratoriano.Oratoriano;
import br.org.gam.api.Entities.oratoriano.persistence.OratorianoEntity;

import java.util.Set;
import java.util.UUID;

public interface GetOratorianoInstance {
    Oratoriano domainById(UUID id);
    OratorianoEntity entityById(UUID id);
    Set<Oratoriano> domainsbyId(Set<UUID> ids);
}
