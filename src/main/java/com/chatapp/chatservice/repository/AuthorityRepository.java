package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository  extends JpaRepository<Authority, Integer> {

    Optional<Authority> findByPermission(String permission);

}
