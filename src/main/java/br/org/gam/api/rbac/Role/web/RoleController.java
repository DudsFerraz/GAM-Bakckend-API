package br.org.gam.api.rbac.role.web;

import br.org.gam.api.rbac.role.application.useCases.getrolePermissions.GetRolePermissions;
import br.org.gam.api.rbac.role.application.useCases.getrolePermissions.GetRolePermissionsRDTO;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/roles")
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
