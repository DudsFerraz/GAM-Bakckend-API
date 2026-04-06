package br.org.gam.api.Entities.events.missa.services.getMissaInstance;

import br.org.gam.api.Entities.events.missa.Missa;
import br.org.gam.api.Entities.events.missa.MissaMapper;
import br.org.gam.api.Entities.events.missa.exception.MissaNotFoundException;
import br.org.gam.api.Entities.events.missa.persistence.MissaEntity;
import br.org.gam.api.Entities.events.missa.persistence.MissaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetMissaInstance implements GetMissaInstance {
    private final MissaRepository missaRepo;
    private final MissaMapper missaMapper;

    public SpringGetMissaInstance(MissaRepository missaRepo, MissaMapper missaMapper) {
        this.missaRepo = missaRepo;
        this.missaMapper = missaMapper;
    }

    @Override
    public Missa domainById(UUID id) {
        return missaRepo.findById(id)
                .map(missaMapper::entityToDomain)
                .orElseThrow(() -> new MissaNotFoundException("Could not find missa with id " + id));
    }

    @Override
    public MissaEntity entityById(UUID id) {
        return missaRepo.findById(id)
                .orElseThrow(() -> new MissaNotFoundException("Could not find missa with id " + id));
    }
}
