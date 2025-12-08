package br.org.gam.api.Entities.RBAC.rolePermission.exception;

public class RolePermissionNotFoundException extends RuntimeException {
    public RolePermissionNotFoundException(String message) {
        super(message);
    }
}
