package br.org.gam.api.Entities.RBAC.role.controller;

import br.org.gam.api.Entities.RBAC.role.services.getRolePermissions.GetRolePermissions;
import br.org.gam.api.Entities.RBAC.role.services.getRolePermissions.GetRolePermissionsRDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/role")
@RestController
public class RoleController {
    private final GetRolePermissions getRolePermissions;

    public RoleController(GetRolePermissions getRolePermissions) {
        this.getRolePermissions = getRolePermissions;
    }

    @GetMapping("/{roleId}/permissions")
    public ResponseEntity<GetRolePermissionsRDTO> getPermissionsById(@PathVariable UUID roleId){

        return ResponseEntity.ok(
                getRolePermissions.allById(roleId)
        );
    }
}
