package com.intern.resource.base.service.impl;


import com.intern.resource.base.entity.Permission;
import com.intern.resource.base.dto.PermissionDTO;
import com.intern.resource.base.mapper.PermissionMapper;
import com.intern.resource.base.repository.PermissionRepository;
import com.intern.resource.base.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionDTO saveEntity(PermissionDTO permissionDTO) {
        if (permissionRepository.existsByName(permissionDTO.getName().toUpperCase()))
            throw new RuntimeException("save permission already exist");
        Permission permission = permissionRepository.save(PermissionMapper.dtoToEntity(permissionDTO));
        return PermissionMapper.entityToDto(permission);
    }

    @Override
    public void deletePermission(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("permission not found"));

        permissionRepository.delete(permission);
    }
}