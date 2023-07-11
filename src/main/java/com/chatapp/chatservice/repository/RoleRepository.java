package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.Authority;
import com.chatapp.chatservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {


    Optional<Role> findByRoleName(String roleName);

    Boolean existsByAuthoritiesIsIn(Collection<Authority> authorities);
}
