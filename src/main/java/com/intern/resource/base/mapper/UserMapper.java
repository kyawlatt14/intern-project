package com.intern.resource.base.mapper;

import com.intern.resource.base.entity.User;
import com.intern.resource.base.dto.UserDTO;

import java.util.stream.Collectors;

public class UserMapper {

    public static User dtoToEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setDisable(dto.isDisable());
        return user;
    }

    public static UserDTO entityToDto(User entity) {
        return UserDTO.builder()
                .userId(entity.getId())
                .name(entity.getName())
                .userName(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .disable(entity.isDisable())
                .roles(entity.getRoles().stream()
                        .map(RoleMapper::entityToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
