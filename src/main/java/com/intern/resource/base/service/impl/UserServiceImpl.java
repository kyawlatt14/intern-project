package com.intern.resource.base.service.impl;

import com.intern.resource.base.entity.Role;
import com.intern.resource.base.entity.User;
import com.intern.resource.base.dto.RoleDTO;
import com.intern.resource.base.dto.UserDTO;
import com.intern.resource.base.mapper.UserMapper;
import com.intern.resource.base.repository.RoleRepository;
import com.intern.resource.base.repository.UserRepository;
import com.intern.resource.base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.getRoles().clear();

        List<Role> updatedRoles = new ArrayList<>();
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepository.findById(roleDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            role.setRoleName(roleDTO.getRoleName());
            role.setDescription(roleDTO.getRoleDesc());
            updatedRoles.add(role);
        }
        existingUser.setRoles(updatedRoles);
        userRepository.save(existingUser);
        return UserMapper.entityToDto(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setDisable(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public List<UserDTO> getUsersByDisableStatus(boolean disable) {
        List<User> users = userRepository.findByDisable(disable);
        for (User user : users) {
            user.getRoles().size();
        }
        return users.stream()
                .map(UserMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<UserDTO> getUserIfDisabled(Long userId) {
        Optional<User> optUser = userRepository.findByIdAndDisable(userId, true);

        return optUser.map(UserMapper::entityToDto);
    }

    @Override
    @Transactional
    public UserDTO addUser(UserDTO userDTO) {
        User user = UserMapper.dtoToEntity(userDTO);
        Role role = roleRepository.findByRoleName(userDTO.getRoles().get(0).getRoleName());
        user.setRoles(Collections.singletonList(role));

        User savedUser = userRepository.save(user);
        return UserMapper.entityToDto(savedUser);
    }

    @Override
    public User assignRolesToUser(Long userId, List<Long> roleIds) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setDisable(false);

        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        user.getRoles().addAll(roles);
        return userRepository.save(user);
    }
}
