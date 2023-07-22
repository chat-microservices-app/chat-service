package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {


     <T> Page<T> findAllByMembers_Member_userId(Pageable pageable, Class<T> type, UUID userId);

     <T> Page<T> findAllByMembers_Member_userIdNot(Pageable pageable, Class<T> type, UUID userId);

     <T> Page<T>  findAllProjectedBy(Pageable pageable, Class<T> type);
}
