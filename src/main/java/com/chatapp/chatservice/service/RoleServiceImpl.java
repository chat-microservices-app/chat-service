package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.Authority;
import com.chatapp.chatservice.domain.Role;
import com.chatapp.chatservice.repository.AuthorityRepository;
import com.chatapp.chatservice.repository.RoleRepository;
import com.chatapp.chatservice.web.mapper.RoleMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final AuthorityRepository authorityRepository;

    private final RoleMapper roleMapper;

    @Override
    public Role updateRole(String role, List<String> permissions) {
        Optional<Role> potentialRole = roleRepository.findByRoleName(role);
        if (potentialRole.isPresent()) {
            Role roleToUpdate = potentialRole.get();
            Set<Authority> auths = addAuthorityToRole(roleToUpdate, permissions);
            roleToUpdate.getAuthorities().addAll(auths);
            return roleRepository.saveAndFlush(roleToUpdate);
        }

        Role newRole = roleMapper.createRole(role);
        Role updatedRole = roleRepository.saveAndFlush(newRole);
        updatedRole.setAuthorities(addAuthorityToRole(updatedRole, permissions));
        return roleRepository.saveAndFlush(updatedRole);
    }

    public Set<Authority> addAuthorityToRole(Role role, List<String> permissions) {
        Set<Authority> auths = new HashSet<>();
        permissions.forEach(permission -> {
            Authority authority = getAuthority(permission);
            if (!roleRepository.existsByAuthoritiesIsIn(Set.of(authority))) {
                auths.add(authority);
            }
        });
        return auths;
    }

    public Authority getAuthority(String permission) {
        Optional<Authority> potentialAuthority = authorityRepository.findByPermission(permission);
        return potentialAuthority.orElseGet(() -> authorityRepository.saveAndFlush(roleMapper.createAuthority(permission)));
    }


}
