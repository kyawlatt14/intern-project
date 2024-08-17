package com.intern.resource.base.mapper;

import com.intern.resource.base.entity.Permission;
import com.intern.resource.base.entity.Role;
import com.intern.resource.base.dto.PermissionDTO;
import com.intern.resource.base.dto.RoleDTO;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {

    public static Role dtoToEntity(RoleDTO dto) {
        return Role.builder()
                .description(dto.getRoleDesc())
                .roleName(dto.getRoleName().toUpperCase())
                .build();
    }

    public static RoleDTO entityToDto(Role entity) {

        if (entity == null) {
            return null;
        }

        List<PermissionDTO> permissionDTOS = entity.getPermissions().stream()
                .map(permission -> PermissionDTO.builder()
                        .id(permission.getId())
                        .name(permission.getName())
                        .form(permission.getForm())
                        .description(permission.getDescription())
                        .build())
                .collect(Collectors.toList());

        List<Long> permissionIds = entity.getPermissions().stream()
                .map(Permission::getId)
                .collect(Collectors.toList());

        return RoleDTO.builder()
                .roleId(entity.getId())
                .roleName(entity.getRoleName())
                .roleDesc(entity.getDescription())
                .permissionDTOS(permissionDTOS)
                .permissionIds(permissionIds)
                .build();
    }
}
