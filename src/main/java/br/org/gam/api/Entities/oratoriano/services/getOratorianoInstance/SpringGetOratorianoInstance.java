package br.org.gam.api.Entities.oratoriano.services.getOratorianoInstance;

import br.org.gam.api.Entities.oratoriano.Oratoriano;
import br.org.gam.api.Entities.oratoriano.OratorianoMapper;
import br.org.gam.api.Entities.oratoriano.exception.OratorianoNotFoundException;
import br.org.gam.api.Entities.oratoriano.persistence.OratorianoEntity;
import br.org.gam.api.Entities.oratoriano.persistence.OratorianoRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class SpringGetOratorianoInstance implements GetOratorianoInstance {
    private final OratorianoRepository oratorianoRepo;
    private final OratorianoMapper oratorianoMapper;

    public SpringGetOratorianoInstance(OratorianoRepository oratorianoRepo, OratorianoMapper oratorianoMapper) {
        this.oratorianoRepo = oratorianoRepo;
        this.oratorianoMapper = oratorianoMapper;
    }

    @Override
    public Oratoriano domainById(UUID id) {
        return oratorianoRepo.findById(id)
                .map(oratorianoMapper::entityToDomain)
                .orElseThrow(() -> new OratorianoNotFoundException("Could not find oratoriano with id " + id));
    }

    @Override
    public OratorianoEntity entityById(UUID id) {
        return oratorianoRepo.findById(id)
                .orElseThrow(() -> new OratorianoNotFoundException("Could not find oratoriano with id " + id));
    }

    @Override
    public Set<Oratoriano> domainsbyId(Set<UUID> ids) {
        return oratorianoRepo.findAllById(ids)
                .stream()
                .map(oratorianoMapper::entityToDomain)
                .collect(Collectors.toSet());
    }
}
