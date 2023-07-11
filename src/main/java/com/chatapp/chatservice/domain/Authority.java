package com.chatapp.chatservice.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


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

    @Column(name = "permission", columnDefinition = "varchar", unique = true, nullable = false)
    private String permission;


    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Authority authority = (Authority) o;
        return getAuthorityId() != null && Objects.equals(getAuthorityId(), authority.getAuthorityId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getAuthorityId());
    }
}
