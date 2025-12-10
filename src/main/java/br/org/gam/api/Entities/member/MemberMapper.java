package br.org.gam.api.Entities.member;

import br.org.gam.api.Entities.account.AccountMapper;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMember.GetMemberRDTO;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMemberRDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {AccountMapper.class}
)
public interface MemberMapper {

    MemberEntity fromDomainToEntity(Member member);

    Member fromEntityToDomain(MemberEntity memberEntity);

    RegisterMemberRDTO fromEntityToRegisterMemberRDTO(MemberEntity memberEntity);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "name.fullName", target = "name")
    GetMemberRDTO fromEntityToGetMemberRDTO(MemberEntity memberEntity);
}
