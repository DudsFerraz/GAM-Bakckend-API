package br.org.gam.api.Entities.presence.services.getPresencesByMember;

import br.org.gam.api.Entities.presence.PresenceMapper;
import br.org.gam.api.Entities.presence.persistence.PresenceRepository;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.services.getPresenceById.GetPresenceByIdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetPresencesByMember implements GetPresencesByMember {
    private final PresenceRepository presenceRepo;
    private final PresenceMapper presenceMapper;

    public SpringGetPresencesByMember(PresenceRepository presenceRepo, PresenceMapper presenceMapper) {
        this.presenceRepo = presenceRepo;
        this.presenceMapper = presenceMapper;
    }

    @Override
    public Page<GetPresenceByIdDTO> getMemberPresences(UUID memberId, Pageable pageable) {

        Page<PresenceEntity> entitiesPage = presenceRepo.findAllByMember_Id(memberId, pageable);
        return entitiesPage.map(presenceMapper::fromEntityToGetPresenceByIdDTO);
    }
}
