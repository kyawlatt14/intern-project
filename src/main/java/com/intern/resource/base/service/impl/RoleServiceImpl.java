package com.intern.resource.base.service.impl;

import com.intern.resource.base.entity.Permission;
import com.intern.resource.base.entity.Role;
import com.intern.resource.base.dto.AddPermissionDTO;
import com.intern.resource.base.dto.RoleDTO;
import com.intern.resource.base.mapper.RoleMapper;
import com.intern.resource.base.repository.PermissionRepository;
import com.intern.resource.base.repository.RoleRepository;
import com.intern.resource.base.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public RoleDTO saveEntity(RoleDTO roleDTO) {
        if (roleRepository.existsByRoleName(roleDTO.getRoleName().toUpperCase()))
            throw new RuntimeException("Role already exists");

        if (roleDTO.getPermissionDTOS() == null) {
            roleDTO.setPermissionDTOS(new ArrayList<>());
        }
        Role role = roleRepository.save(RoleMapper.dtoToEntity(roleDTO));
        return RoleMapper.entityToDto(role);
    }

    @Override
    @Transactional
    public String addPermission(AddPermissionDTO addPermissionDTO) {
        Role role = roleRepository.findById(addPermissionDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        List<Permission> newPermissions = permissionRepository.findAllById(addPermissionDTO.getPermissionIds());

        if (role.getPermissions() == null) {
            role.setPermissions(new ArrayList<>());
        }

        for (Permission permission : newPermissions) {
            if(!role.getPermissions().contains(permission)) {
                role.getPermissions().add(permission);
            }
        }
        roleRepository.save(role);
        return "Permissions added successfully";
    }

    @Override
    @Transactional
    public RoleDTO getPermissionsByRoleId(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return RoleMapper.entityToDto(role);
    }

    @Override
    @Transactional
    public void removePermissionsByRoleId(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        role.removePermission(permission);
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        roleRepository.delete(role);
    }
}
