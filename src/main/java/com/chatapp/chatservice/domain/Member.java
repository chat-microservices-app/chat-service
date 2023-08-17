package com.chatapp.chatservice.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Room room;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Member member = (Member) o;
        return getMemberId() != null && Objects.equals(getMemberId(), member.getMemberId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getMemberId());
    }
}
