package com.chatapp.chatservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id", columnDefinition = "uuid", updatable = false)
    private String messageId;


    @Column(name = "updated_at", columnDefinition = "timestamp")
    private OffsetDateTime updatedAt;

    @Column(name = "created_at", columnDefinition = "timestamp")
    private OffsetDateTime createdAt;

    @Column(name = "message", columnDefinition = "nvarchar")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdBy;


}