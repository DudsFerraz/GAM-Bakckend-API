package br.org.gam.api.common.auth.refreshToken;

import br.org.gam.api.Entities.account.AccountMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class} )
public interface RefreshTokenMapper {
    RefreshTokenEntity domainToEntity(RefreshToken refreshToken);
    RefreshToken entityToDomain(RefreshTokenEntity refreshTokenEntity);
}
