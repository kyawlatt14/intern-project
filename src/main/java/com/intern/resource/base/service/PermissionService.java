package com.intern.resource.base.service;

import com.intern.resource.base.dto.PermissionDTO;

public interface PermissionService {
    PermissionDTO saveEntity(PermissionDTO permissionDTO);
    void deletePermission(Long permissionId);
}
