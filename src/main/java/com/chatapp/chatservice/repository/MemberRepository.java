package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    Boolean existsByRoles_RoleNameInAndRoom_RoomId(Set<String> roleName, UUID room_roomId);

    Page<Member> findByRoom_RoomId(UUID roomId, Pageable pageable);
}
