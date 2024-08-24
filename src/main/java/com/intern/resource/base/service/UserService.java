package com.intern.resource.base.service;

import com.intern.resource.base.dto.UserDTO;
import com.intern.resource.base.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);

    List<UserDTO> getUsersByDisableStatus(boolean disable);

    Optional<UserDTO> getUserIfDisabled(Long userId);

    UserDTO addUser(UserDTO userDTO);

    User assignRolesToUser(Long userId, List<Long> roleIds);
}
