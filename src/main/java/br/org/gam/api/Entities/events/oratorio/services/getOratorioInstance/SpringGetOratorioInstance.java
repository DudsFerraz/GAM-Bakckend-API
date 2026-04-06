package br.org.gam.api.Entities.events.oratorio.services.getOratorioInstance;

import br.org.gam.api.Entities.events.oratorio.Oratorio;
import br.org.gam.api.Entities.events.oratorio.OratorioMapper;
import br.org.gam.api.Entities.events.oratorio.exception.OratorioNotFoundException;
import br.org.gam.api.Entities.events.oratorio.persistence.OratorioEntity;
import br.org.gam.api.Entities.events.oratorio.persistence.OratorioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetOratorioInstance implements GetOratorioInstance {
    private final OratorioRepository oratorioRepo;
    private final OratorioMapper oratorioMapper;

    public SpringGetOratorioInstance(OratorioRepository oratorioRepo, OratorioMapper oratorioMapper) {
        this.oratorioRepo = oratorioRepo;
        this.oratorioMapper = oratorioMapper;
    }

    @Override
    public Oratorio domainById(UUID id) {
        return oratorioRepo.findById(id)
                .map(oratorioMapper::entityToDomain)
                .orElseThrow(() -> new OratorioNotFoundException("Could not find oratorio with id " + id));
    }

    @Override
    public OratorioEntity entityById(UUID id) {
        return oratorioRepo.findById(id)
                .orElseThrow(() -> new OratorioNotFoundException("Could not find oratorio with id " + id));
    }
}
