package br.org.gam.api.Entities.events.missa.services.getMissa;

import br.org.gam.api.Entities.events.generic.exception.EventNotFoundException;
import br.org.gam.api.Entities.events.generic.persistence.EventEntity;
import br.org.gam.api.Entities.events.generic.security.EventSecurity;
import br.org.gam.api.Entities.events.generic.services.getEventInstance.GetEventInstance;
import br.org.gam.api.Entities.events.missa.MissaMapper;
import br.org.gam.api.Entities.events.missa.persistence.MissaEntity;
import br.org.gam.api.Entities.events.missa.services.MissaRDTO;
import br.org.gam.api.Entities.events.missa.services.getMissaInstance.GetMissaInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetMissa implements GetMissa {
    private final MissaMapper missaMapper;
    private final GetMissaInstance getMissaInstance;
    private final GetEventInstance getEventInstance;
    private final EventSecurity eventSecurity;

    public SpringGetMissa(MissaMapper missaMapper, GetMissaInstance getMissaInstance, GetEventInstance getEventInstance, EventSecurity eventSecurity) {
        this.missaMapper = missaMapper;
        this.getMissaInstance = getMissaInstance;
        this.getEventInstance = getEventInstance;
        this.eventSecurity = eventSecurity;
    }

    @Override
    public MissaRDTO byId(UUID id) {
        EventEntity eventEntity = getEventInstance.entityById(id);
        if(!eventSecurity.canGetEvent(eventEntity)) throw new EventNotFoundException("Could not find missa with id " + id);

        MissaEntity missaEntity = getMissaInstance.entityById(id);
        return missaMapper.entityToRDTO(missaEntity);
    }
}
