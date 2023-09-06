package com.chatapp.chatservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_read_only")
public class User {

    // Do not generate the id, expect the user management service to provide it
    @Id
    @Column(name = "user_id", columnDefinition = "uuid")
    private UUID userId;


    @JsonProperty("username")
    private String username;

    @JsonProperty("firstName")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "picture_url")
    private String pictureUrl;


    // if we delete a user, we want to delete all the messages that they created
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Message> messages = new HashSet<>();


    // if we delete a user, we want to delete all the rooms that they created
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Member> usersInRooms = new HashSet<>();



}

