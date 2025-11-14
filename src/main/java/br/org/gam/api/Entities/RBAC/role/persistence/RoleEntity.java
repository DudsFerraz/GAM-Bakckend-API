package br.org.gam.api.Entities.RBAC.role.persistence;

import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionEntity;
import br.org.gam.api.common.persistence.FullAuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SQLRestriction("deleted_at IS NULL")
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity extends FullAuditableEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<AccountRoleEntity> accountRoles = new HashSet<>();

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<RolePermissionEntity> rolePermissions = new HashSet<>();
}