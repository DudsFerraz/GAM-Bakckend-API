package br.org.gam.api.account.persistence;

import br.org.gam.api.account.Email;
import br.org.gam.api.account.EmailConverter;
import br.org.gam.api.account.PermissionLevelEnum;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "accounts")
public class AccountEntity {

    private static final TimeBasedEpochGenerator uuidV7Generator = Generators.timeBasedEpochGenerator();

    @Id
    private UUID id;

    @Convert(converter = EmailConverter.class)
    @Column(name = "email", unique = true, nullable = false)
    private Email email;

    @Column(name= "password" ,nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
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
