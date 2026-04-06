package br.org.gam.api.Entities.events.missa.services.getMissa;

import br.org.gam.api.Entities.events.missa.persistence.MissaEntity;
import br.org.gam.api.Entities.events.missa.services.MissaRDTO;

import java.util.UUID;

public interface GetMissa {
    MissaRDTO byId(UUID id);
}
