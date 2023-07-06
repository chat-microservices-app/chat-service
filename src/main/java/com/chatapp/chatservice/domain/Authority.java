package com.chatapp.chatservice.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "authority")
public class Authority {


    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "authority_id", columnDefinition = "integer", updatable = false)
    private Integer authorityId;

    @Column(name = "permission", columnDefinition = "varchar")
    private String permission;





}
