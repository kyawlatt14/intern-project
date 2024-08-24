package com.intern.resource.user;

import com.intern.resource.base.dto.UserDTO;
import com.intern.resource.base.entity.User;
import com.intern.resource.base.mapper.UserMapper;
import com.intern.resource.base.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
@Tag(name = "ADMIN-CONTROLLER")
public class AdminController {

    private final UserService userService;

    @PostMapping("/assign-role")
    public ResponseEntity<UserDTO> assignRoleUser(@RequestParam Long userId,
                                                  @RequestParam List<Long> roleIds) {
        User updatedUser = userService.assignRolesToUser(userId, roleIds);
        UserDTO userDTO = UserMapper.entityToDto(updatedUser);
        return ResponseEntity.ok(userDTO);
    }
}
