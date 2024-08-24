package com.intern.resource.base.repository;

import com.intern.resource.base.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRoleName(String upperCase);

    Role findByRoleName(String user);

    List<Role> findAllByIdIn(List<Long> roleIds);
}
