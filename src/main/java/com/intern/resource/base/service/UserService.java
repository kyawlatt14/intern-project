package com.intern.resource.base.service;

import com.intern.resource.base.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    List<UserDTO> getUsersByDisableStatus(boolean disable);
    Optional<UserDTO> getUserIfDisabled(Long userId);
}
