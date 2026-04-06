package br.org.gam.api.Entities.events.oratorio.services.createOratorio;

import br.org.gam.api.Entities.events.generic.Event;
import br.org.gam.api.Entities.events.generic.services.createEvent.CreateEvent;
import br.org.gam.api.Entities.events.generic.services.createEvent.CreateEventRDTO;
import br.org.gam.api.Entities.events.generic.services.getEventInstance.GetEventInstance;
import br.org.gam.api.Entities.events.oratorio.Oratorio;
import br.org.gam.api.Entities.events.oratorio.OratorioMapper;
import br.org.gam.api.Entities.events.oratorio.persistence.OratorioEntity;
import br.org.gam.api.Entities.events.oratorio.persistence.OratorioRepository;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import br.org.gam.api.Entities.oratoriano.Oratoriano;
import br.org.gam.api.Entities.oratoriano.services.getOratorianoInstance.GetOratorianoInstance;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Transactional
@Service
public class SpringCreateOratorio implements CreateOratorio {
    private final OratorioRepository oratorioRepo;
    private final CreateEvent createEventService;
    private final GetEventInstance getEventInstanceService;
    private final GetMemberInstance getMemberInstanceService;
    private final GetOratorianoInstance getOratorianoInstanceService;
    private final OratorioMapper oratorioMapper;

    public SpringCreateOratorio(OratorioRepository oratorioRepo, CreateEvent createEventService, GetEventInstance getEventInstanceService, GetMemberInstance getMemberInstanceService, GetOratorianoInstance getOratorianoInstanceService, OratorioMapper oratorioMapper) {
        this.oratorioRepo = oratorioRepo;
        this.createEventService = createEventService;
        this.getEventInstanceService = getEventInstanceService;
        this.getMemberInstanceService = getMemberInstanceService;
        this.getOratorianoInstanceService = getOratorianoInstanceService;
        this.oratorioMapper = oratorioMapper;
    }

    @Transactional
    @Override
    public CreateOratorioRDTO create(CreateOratorioDTO dto) {
        CreateEventRDTO newEventRdto = createEventService.create(dto.event());
        Event newEvent = getEventInstanceService.domainById(newEventRdto.id());

        Set<Member> lancheMembers = getMemberInstanceService.domainsById(dto.lancheMembersIds());
        Set<Member> btJovensMembers = getMemberInstanceService.domainsById(dto.btJovensMembersIds());
        Set<Member> btCriancasMembers = getMemberInstanceService.domainsById(dto.btCriancasMembersIds());
        Set<Oratoriano> oratorianos = getOratorianoInstanceService.domainsbyId(dto.oratorianosIds());

        Oratorio newOratorio = Oratorio.register(newEvent, lancheMembers, btJovensMembers, btCriancasMembers, oratorianos);

        OratorioEntity newOratorioEntity = oratorioMapper.domainToEntity(newOratorio);
        OratorioEntity savedOratorioEntity = oratorioRepo.save(newOratorioEntity);

        return oratorioMapper.entityToCreateOratorioRDTO(savedOratorioEntity);
    }
}
