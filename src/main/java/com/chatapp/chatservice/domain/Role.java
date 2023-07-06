package com.chatapp.chatservice.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", columnDefinition = "integer", updatable = false)
    private Integer roleId;

    @Column(name = "role_name", columnDefinition = "varchar")
    private String roleName;


    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "role_authority",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id",
                    columnDefinition = "integer", foreignKey = @ForeignKey(name = "fk_role_authority_role_id")),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "authority_id",
                    columnDefinition = "integer", foreignKey = @ForeignKey(name = "fk_role_authority_authority_id"))
    )
    private Set<Authority> authorities = new HashSet<>();


}
