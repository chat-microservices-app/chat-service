package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {


    <T> Page<T> findAllByMembers_Member_userId(Pageable pageable, Class<T> type, UUID userId);

    @Query(value = "SELECT DISTINCT r " +
            "from Room r " +
            "WHERE r.roomId NOT IN " +
            "(SELECT m.roomId FROM Room m " +
            "INNER JOIN m.members member " +
            "WHERE member.member.userId = :userId)",
            countQuery = "SELECT COUNT(DISTINCT r) " +
                    "from Room r " +
                    "WHERE r.roomId NOT IN " +
                    "(SELECT m.roomId FROM Room m " +
                    "INNER JOIN m.members member " +
                    "WHERE member.member.userId = :userId)"
    )
    <T> Page<T> findAllByMembers_Member_userIdNot(Pageable pageable, Class<T> type, @Param("userId") UUID userId);


    <T> Page<T> findAllProjectedBy(Pageable pageable, Class<T> type);

}
