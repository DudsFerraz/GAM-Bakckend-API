package br.org.gam.api.Entities.RBAC.accountRole.persistence;

import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.common.persistence.ISoftDeletable;
import br.org.gam.api.common.persistence.JunctionAuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@SQLRestriction("deleted_at IS NULL")
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "account_roles")
public class AccountRoleEntity extends JunctionAuditableEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}