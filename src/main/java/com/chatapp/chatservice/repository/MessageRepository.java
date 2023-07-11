package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;



public interface MessageRepository extends JpaRepository<Message, UUID> {


    <T> Page<T> findByRoom_RoomId(@Param("roomId") UUID roomId,
                                            Pageable pageable, Class<T> type);
}
