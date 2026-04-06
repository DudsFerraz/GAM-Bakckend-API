package br.org.gam.api.Entities.events.missa.services.getMissaInstance;

import br.org.gam.api.Entities.events.missa.Missa;
import br.org.gam.api.Entities.events.missa.persistence.MissaEntity;

import java.util.UUID;

public interface GetMissaInstance {
    Missa domainById(UUID id);
    MissaEntity entityById(UUID id);
}
