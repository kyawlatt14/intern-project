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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    public UserDTO saveUser(UserDTO userDTO) {
        User user = UserMapper.dtoToEntity(userDTO);
        user.setDisable(true);
        if(CollectionUtils.isEmpty(user.getRoles())){
             Role role = Role.builder()
                     .roleName("USER")
                     .description("test_user")
                     .build();
            role.setUser(user);
        }else{
            for (Role role : user.getRoles()) {
                role.setUser(user);
            }
        }
        User savedUser = userRepository.save(user);
        return UserMapper.entityToDto(savedUser);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDTO.getName());
        existingUser.setUserName(userDTO.getUserName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());

        existingUser.getRoles().clear();

        List<Role> updatedRoles = new ArrayList<>();
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepository.findById(roleDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            role.setRoleName(roleDTO.getRoleName());
            role.setDescription(roleDTO.getRoleDesc());
            role.setUser(existingUser);
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
}
