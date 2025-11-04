package br.org.gam.api.Entities.account.persistence;

import br.org.gam.api.Entities.account.common.Email;
import br.org.gam.api.Entities.account.common.PermissionLevelEnum;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {

    private static final TimeBasedEpochGenerator uuidV7Generator = Generators.timeBasedEpochGenerator();

    @Id
    private UUID id;

    @Convert(converter = EmailConverterJPA.class)
    @Column(name = "email", unique = true, nullable = false)
    private Email email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "permission_level")
    private PermissionLevelEnum permissionLevel;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = uuidV7Generator.generate();
        }
    }
}
