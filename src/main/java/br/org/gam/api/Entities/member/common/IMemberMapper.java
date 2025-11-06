package br.org.gam.api.Entities.member.common;

import br.org.gam.api.Entities.member.domain.Member;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMemberById.dto.GetMemberByIdDTO;
import br.org.gam.api.Entities.member.services.registerMember.dto.RegisterMemberResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMemberMapper {

    MemberEntity fromDomainToEntity(Member member);

    Member fromEntityToDomain(MemberEntity memberEntity);

    RegisterMemberResponseDTO fromEntityToRegisterMemberResponseDTO(MemberEntity memberEntity);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "name.fullName", target = "name")
    GetMemberByIdDTO fromEntityToGetMemberByIdDTO(MemberEntity memberEntity);
}
