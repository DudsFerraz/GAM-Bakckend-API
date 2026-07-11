package br.org.gam.api.security.refreshtoken.persistence;

import java.util.Optional;
import java.util.UUID;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select refreshToken from RefreshTokenEntity refreshToken where refreshToken.token = :token")
    Optional<RefreshTokenEntity> findByTokenForUpdate(@Param("token") UUID token);

    Optional<RefreshTokenEntity> findByToken(UUID token);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from RefreshTokenEntity refreshToken where refreshToken.token = :token")
    void deleteByToken(@Param("token") UUID token);
}
