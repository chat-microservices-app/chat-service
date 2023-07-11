package com.chatapp.chatservice.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.proxy.HibernateProxy;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id", columnDefinition = "uuid", updatable = false)
    private UUID roomId;


    @Column(name = "room_name", columnDefinition = "varchar")
    private String name;

    @Column(name = "picture_url", columnDefinition = "varchar")
    private String pictureUrl;

    @Column(name = "created_at", columnDefinition = "timestamp")
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @Builder.Default
    private List<Message> messages = new ArrayList<>();


    public void addMember(Member member) {
        members.add(member);
        member.setRoom(this);
    }


    public void removeMember(Member member) {
        members.remove(member);
        member.setRoom(null);
    }




    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Room room = (Room) o;
        return getRoomId() != null && Objects.equals(getRoomId(), room.getRoomId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
