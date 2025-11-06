package br.org.gam.api.Entities.member.services.registerMember.service;

import br.org.gam.api.Entities.member.services.registerMember.dto.RegisterMemberDTO;
import br.org.gam.api.Entities.member.services.registerMember.dto.RegisterMemberResponseDTO;

public interface IRegisterMemberService {
    RegisterMemberResponseDTO registerMember(RegisterMemberDTO dto);
}
