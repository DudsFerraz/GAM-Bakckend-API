package br.org.gam.api.Entities.presence.services.getPresencesByMember;

import br.org.gam.api.Entities.presence.services.getPresenceById.GetPresenceByIdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IGetPresencesByMemberService {
    Page<GetPresenceByIdDTO> getMemberPresences(UUID memberId, Pageable pageable);
}
