package br.org.gam.api.member.solicitation.application;

import br.org.gam.api.account.application.AccountMapper;
import br.org.gam.api.member.solicitation.persistence.MembershipSolicitationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface MembershipSolicitationMapper {

    // =====================================================================================
    // Persistence -> RDTO
    // =====================================================================================

    @Mapping(target = "firstName", source = "name.firstName")
    @Mapping(target = "surname", source = "name.surname")
    @Mapping(target = "submittedAt", source = "createdAt")
    @Mapping(target = "memberId", source = "member.id")
    MembershipSolicitationRDTO entityToRDTO(MembershipSolicitationEntity entity);
}
