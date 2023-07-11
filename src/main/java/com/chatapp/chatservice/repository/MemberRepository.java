package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    Boolean existsByRolesInAndRoom_RoomId(Set<String> role, UUID roomId);
}
