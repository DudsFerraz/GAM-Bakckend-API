package br.org.gam.api.Entities.RBAC.permission.persistence;

import br.org.gam.api.common.persistence.FullAuditableEntity;
import br.org.gam.api.common.persistence.JunctionAuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class PermissionEntity extends JunctionAuditableEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;
}