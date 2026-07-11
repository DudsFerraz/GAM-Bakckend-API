package br.org.gam.api.security.refreshtoken.application;

import br.org.gam.api.account.application.AccountDomainLoader;
import br.org.gam.api.account.domain.Account;
import br.org.gam.api.shared.domain.GamEmail;
import br.org.gam.api.account.persistence.AccountEntity;
import br.org.gam.api.security.application.InvalidTokenFormatException;
import br.org.gam.api.security.application.RefreshTokenExpiredException;
import br.org.gam.api.security.application.TokenNotFoundException;
import br.org.gam.api.security.application.TokensDTO;
import br.org.gam.api.security.jwt.JwtService;
import br.org.gam.api.security.refreshtoken.domain.RefreshToken;
import br.org.gam.api.security.refreshtoken.persistence.RefreshTokenEntity;
import br.org.gam.api.security.refreshtoken.persistence.RefreshTokenRepository;
import br.org.gam.api.shared.persistence.UUIDGenerator;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private final Long refreshTokenDurationMs;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccountDomainLoader getAccountInstance;
    private final RefreshTokenMapper refreshTokenMapper;
    private final JwtService jwtService;
    private final UserDetailsService accountDetailsService;

    public RefreshTokenService(@Value("${jwt.refresh-expiration-ms}") Long refreshTokenExpiration,
                               RefreshTokenRepository refreshTokenRepository, AccountDomainLoader getAccountInstance,
                               RefreshTokenMapper refreshTokenMapper, JwtService jwtService, UserDetailsService accountDetailsService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.getAccountInstance = getAccountInstance;
        this.refreshTokenMapper = refreshTokenMapper;
        this.refreshTokenDurationMs = refreshTokenExpiration;
        this.jwtService = jwtService;
        this.accountDetailsService = accountDetailsService;
    }

    public RefreshTokenEntity findByToken(String tokenString) {
        UUID tokenUUID = parseRefreshToken(tokenString);

        return refreshTokenRepository.findByToken(tokenUUID)
                .orElseThrow(() -> new TokenNotFoundException("Refresh token is not in database!"));
    }

    @Transactional
    public UUID createRefreshToken(GamEmail email) {

        UUID token = UUIDGenerator.generateUUIDV4();
        Instant expiryDate = Instant.now().plusMillis(refreshTokenDurationMs);
        Account account = getAccountInstance.requiredByEmail(email);

        RefreshToken newRefreshToken = RefreshToken.register(token, expiryDate, account);
        RefreshTokenEntity newRefreshTokenEntity = refreshTokenMapper.domainToEntity(newRefreshToken);
        RefreshTokenEntity savedRefreshTokenEntity = refreshTokenRepository.save(newRefreshTokenEntity);

        return savedRefreshTokenEntity.getToken();
    }

    public void verifyExpiration(RefreshTokenEntity token) {
        if (!token.getExpiryDate().isAfter(Instant.now())) {
            refreshTokenRepository.deleteByToken(token.getToken());
            throw new RefreshTokenExpiredException("Refresh token was expired. Please make a new signin request");
        }
    }

    @Transactional(dontRollbackOn = RefreshTokenExpiredException.class)
    public TokensDTO refresh(String refreshTokenStr){
        RefreshTokenEntity oldTokenEntity = findByTokenForUpdate(refreshTokenStr);
        this.verifyExpiration(oldTokenEntity);

        AccountEntity accountEntity = oldTokenEntity.getAccount();
        GamEmail accountEmail = accountEntity.getEmail();
        UUID accountId = accountEntity.getId();

        refreshTokenRepository.deleteByToken(oldTokenEntity.getToken());

        UUID newRefreshToken = this.createRefreshToken(accountEmail);

        UserDetails accountDetails = accountDetailsService.loadUserByUsername(accountId.toString());
        String newAccessToken = jwtService.generateToken(accountDetails);

        return new TokensDTO(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void logout(String refreshTokenStr){
        if (refreshTokenStr == null || refreshTokenStr.isBlank()) {
            return;
        }

        UUID token;
        try {
            token = parseRefreshToken(refreshTokenStr);
        } catch (InvalidTokenFormatException ignored) {
            return;
        }

        refreshTokenRepository.deleteByToken(token);
    }

    private UUID parseRefreshToken(String tokenString) {
        if (tokenString == null || tokenString.isBlank()) {
            throw new InvalidTokenFormatException("token has invalid format");
        }

        try {
            UUID token = UUID.fromString(tokenString);
            if (token.version() != 4 || !token.toString().equalsIgnoreCase(tokenString)) {
                throw new InvalidTokenFormatException("token has invalid format");
            }
            return token;
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenFormatException("token has invalid format");
        }
    }

    private RefreshTokenEntity findByTokenForUpdate(String tokenString) {
        UUID tokenUUID = parseRefreshToken(tokenString);
        return refreshTokenRepository.findByTokenForUpdate(tokenUUID)
                .orElseThrow(() -> new TokenNotFoundException("Refresh token is not in database!"));
    }
}
