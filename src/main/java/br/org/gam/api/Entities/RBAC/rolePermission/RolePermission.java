package br.org.gam.api.Entities.RBAC.rolePermission;

import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.common.persistence.UUIDGenerator;

import java.util.Objects;
import java.util.UUID;

public class RolePermission {
    private UUID id;
    private RoleEntity role;
    private PermissionEntity permission;

    /**
     * @deprecated <b>ESTE CONSTRUTOR É EXCLUSIVO PARA USO INTERNO E JPA/MapStruct.</b>
     * <br> <br>
     * <b> Use o método fábrica {@link #register(RoleEntity role, PermissionEntity permission)}.
     */
    @Deprecated
    RolePermission(UUID id, RoleEntity role, PermissionEntity permission) {
        this.id = id;
        this.role = role;
        this.permission = permission;
    }

    public static RolePermission register(RoleEntity role, PermissionEntity permission) {
        Objects.requireNonNull(role, "role cannot be null");
        Objects.requireNonNull(permission, "permission cannot be null");

        UUID id = UUIDGenerator.generateUUIDV7();

        return new RolePermission(id, role, permission);
    }

    public UUID getId() {
        return id;
    }

    public RoleEntity getRole() {
        return role;
    }

    public PermissionEntity getPermission() {
        return permission;
    }
}
