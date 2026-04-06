package br.org.gam.api.Entities.events.missa.services.createMissa;

import br.org.gam.api.Entities.events.generic.Event;
import br.org.gam.api.Entities.events.generic.services.createEvent.CreateEvent;
import br.org.gam.api.Entities.events.generic.services.createEvent.CreateEventRDTO;
import br.org.gam.api.Entities.events.generic.services.getEventInstance.GetEventInstance;
import br.org.gam.api.Entities.events.missa.Missa;
import br.org.gam.api.Entities.events.missa.MissaMapper;
import br.org.gam.api.Entities.events.missa.persistence.MissaEntity;
import br.org.gam.api.Entities.events.missa.persistence.MissaRepository;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Transactional
@Service
public class SpringCreateMissa implements CreateMissa {
    private final CreateEvent createEventService;
    private final GetEventInstance getEventInstanceService;
    private final GetMemberInstance getMemberInstanceService;
    private final MissaMapper missaMapper;
    private final MissaRepository missaRepo;
    public SpringCreateMissa(CreateEvent createEventService, GetEventInstance getEventInstanceService, GetMemberInstance getMemberInstanceService, MissaMapper missaMapper, MissaRepository missaRepo) {
        this.createEventService = createEventService;
        this.getEventInstanceService = getEventInstanceService;
        this.getMemberInstanceService = getMemberInstanceService;
        this.missaMapper = missaMapper;
        this.missaRepo = missaRepo;
    }

    @Transactional
    @Override
    public CreateMissaRDTO createMissa(CreateMissaDTO dto) {
        CreateEventRDTO newEventRdto = createEventService.create(dto.event());
        Event newEvent = getEventInstanceService.domainById(newEventRdto.id());


        Member comentariosMember = resolveMember(dto.comentariosMemberId());
        Member leitura1Member = resolveMember(dto.leitura1MemberId());
        Member salmoMember = resolveMember(dto.salmoMemberId());
        Member leitura2Member = resolveMember(dto.leitura2MemberId());
        Member precesMember = resolveMember(dto.precesMemberId());
        Set<Member> acolhidaMembers = getMemberInstanceService.domainsById(dto.acolhidaMembersIds());

        Missa newMissa = Missa.register(newEvent, comentariosMember, leitura1Member, salmoMember,  leitura2Member, precesMember, acolhidaMembers);

        MissaEntity newEntity = missaMapper.domainToEntity(newMissa);
        MissaEntity savedEntity = missaRepo.save(newEntity);

        return missaMapper.entityToCreateMissaRDTO(savedEntity);
    }

    private Member resolveMember(UUID memberId) {
        if (memberId == null) return null;
        return getMemberInstanceService.domainById(memberId);
    }

}
