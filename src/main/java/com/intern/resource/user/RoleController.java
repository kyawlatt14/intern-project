package com.intern.resource.user;

import com.intern.resource.base.dto.AddPermissionDTO;
import com.intern.resource.base.dto.RoleDTO;
import com.intern.resource.base.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
@Tag(name = "ROLES-CONTROLLER")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("save-role")
    public ResponseEntity<?> saveRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.saveEntity(roleDTO));
    }

    @PostMapping("add-permission")
    public ResponseEntity<String> addPermission(@RequestBody AddPermissionDTO addPermissionDTO) {
        return ResponseEntity.ok(roleService.addPermission(addPermissionDTO));
    }

    @GetMapping("role-permissions")
    public ResponseEntity<RoleDTO> getPermissionsByRoleId(@RequestParam Long roleId) {
        RoleDTO roleDTO = roleService.getPermissionsByRoleId(roleId);
        return ResponseEntity.ok(roleDTO);
    }

    @DeleteMapping("permissions")
    public ResponseEntity<Void> removePermissionsByRoleId(@RequestParam Long roleId, @RequestParam Long permissionId) {
        roleService.removePermissionsByRoleId(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("roles")
    public ResponseEntity<Void> deleteRole(@RequestParam Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}