package com.intern.resource.user;

import com.intern.resource.base.dto.PermissionDTO;
import com.intern.resource.base.service.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permissions")
@Tag(name = "PERMISSION-CONTROLLER")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("save-permission")
    public ResponseEntity<?> savePermission(@RequestBody PermissionDTO permissionDTO) {
        return ResponseEntity.ok(permissionService.saveEntity(permissionDTO));
    }

    @DeleteMapping("delete-permissions")
    public ResponseEntity<Void> deletePermission(@RequestParam Long permissionId) {
        permissionService.deletePermission(permissionId);
        return ResponseEntity.noContent().build();
    }
}