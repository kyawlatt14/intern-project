package com.intern.resource.base.service;

import com.intern.resource.base.dto.AddPermissionDTO;
import com.intern.resource.base.dto.RoleDTO;

public interface RoleService {
    RoleDTO saveEntity(RoleDTO roleDTO);
    String addPermission( AddPermissionDTO addPermissionDTO);
    RoleDTO getPermissionsByRoleId(Long roleId);
    void removePermissionsByRoleId(Long roleId, Long permissionId);
    void deleteRole(Long roleId);
}
