package com.chatapp.chatservice.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", columnDefinition = "uuid", updatable = false)
    private UUID memberId;

    @Column(name = "member_name", columnDefinition = "varchar")
    private String memberName;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    @JoinColumn(name = "room_id")
    private Room room;


    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "member_id",
                    columnDefinition = "uuid", foreignKey = @ForeignKey(name = "fk_member_role_member_id")),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id",
                    columnDefinition = "integer", foreignKey = @ForeignKey(name = "fk_member_role_role_id"))
    )
    @Fetch(FetchMode.JOIN)
    @Singular
    private Set<Role> roles = new HashSet<>();




}
