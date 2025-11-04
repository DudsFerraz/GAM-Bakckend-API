package br.org.gam.api.Entities.member.persistence;

import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.member.MemberStatusEnum;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "members")
public class MemberEntity {

    private static final TimeBasedEpochGenerator uuidV7Generator = Generators.timeBasedEpochGenerator();

    @Id
    private UUID id;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status")
    private MemberStatusEnum status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, unique = true)
    private AccountEntity account;

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
