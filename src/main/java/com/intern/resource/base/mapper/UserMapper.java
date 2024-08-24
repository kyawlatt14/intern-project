package com.intern.resource.base.mapper;

import com.intern.resource.base.entity.Role;
import com.intern.resource.base.entity.User;
import com.intern.resource.base.dto.UserDTO;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class UserMapper {

    public static User dtoToEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setImagePath(dto.getImagePath());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setDisable(dto.isDisable());

        user.setRoles(dto.getRoles().stream()
                .map(roleDTO -> {
                    Role role = new Role();
                    role.setRoleName(roleDTO.getRoleName());
                    role.setDescription(roleDTO.getRoleDesc());
                    return role;
                })
                .collect(Collectors.toList()));

        return user;
    }

    public static UserDTO entityToDto(User entity) {
        return UserDTO.builder()
                .userId(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .disable(entity.isDisable())
                .roles(ObjectUtils.isEmpty(entity.getRoles())?new ArrayList<>():entity.getRoles().stream()
                        .map(RoleMapper::entityToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
