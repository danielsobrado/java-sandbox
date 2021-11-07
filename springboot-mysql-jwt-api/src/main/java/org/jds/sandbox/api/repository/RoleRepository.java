package org.jds.sandbox.api.repository;

import java.util.Optional;

import org.jds.sandbox.api.model.Role;
import org.jds.sandbox.api.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}