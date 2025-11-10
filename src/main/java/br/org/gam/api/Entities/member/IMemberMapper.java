package br.org.gam.api.Entities.member;

import br.org.gam.api.Entities.account.IAccountMapper;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMemberById.GetMemberByIdDTO;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMemberResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {IAccountMapper.class}
)
public interface IMemberMapper {

    MemberEntity fromDomainToEntity(Member member);

    Member fromEntityToDomain(MemberEntity memberEntity);

    RegisterMemberResponseDTO fromEntityToRegisterMemberResponseDTO(MemberEntity memberEntity);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "name.fullName", target = "name")
    GetMemberByIdDTO fromEntityToGetMemberByIdDTO(MemberEntity memberEntity);
}
