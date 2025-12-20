package br.org.gam.api.common.auth.refreshToken;

import br.org.gam.api.Entities.account.AccountMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class} )
public interface RefreshTokenMapper {
    RefreshTokenEntity fromDomainToEntity(RefreshToken refreshToken);
    RefreshToken fromEntityToDomain(RefreshTokenEntity refreshTokenEntity);
}
