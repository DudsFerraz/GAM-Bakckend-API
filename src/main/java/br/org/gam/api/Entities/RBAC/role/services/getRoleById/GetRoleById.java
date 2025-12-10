package br.org.gam.api.Entities.RBAC.role.services.getRoleById;

import java.util.UUID;

public interface GetRoleById {
    GetRoleByIdRDTO getRoleById(UUID id);
}
