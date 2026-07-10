package br.org.gam.api.account.persistence;

import br.org.gam.api.rbac.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.shared.auditing.FullAuditableEntity;
import br.org.gam.api.shared.domain.GamEmail;
import br.org.gam.api.shared.domain.GamEmailConverterJPA;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction("deleted_at is NULL")
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity extends FullAuditableEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Convert(converter = GamEmailConverterJPA.class)
    @Column(name = "email", nullable = false)
    private GamEmail email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @OneToMany(mappedBy = "account")
    @BatchSize(size = 20)
    private Set<AccountRoleEntity> accountRoles;
}
