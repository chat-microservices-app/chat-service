package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.Member;
import com.chatapp.chatservice.domain.Room;
import com.chatapp.chatservice.domain.User;
import com.chatapp.chatservice.enums.DefaultRole;
import com.chatapp.chatservice.repository.MemberRepository;
import com.chatapp.chatservice.repository.RoomRepository;
import com.chatapp.chatservice.repository.UserRepository;
import com.chatapp.chatservice.utils.RoleHandler;
import com.chatapp.chatservice.web.dto.RoomDTO;
import com.chatapp.chatservice.web.dto.RoomForm;
import com.chatapp.chatservice.web.dto.RoomUpdateForm;
import com.chatapp.chatservice.web.mapper.RoomMapper;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;


@Transactional
@Log4j2
@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final UserRepository userRepository;

    private final MemberRepository memberRepository;

    private final RoleService roleService;


    @Override
    public ObjectPagedList<RoomDTO> getRoomList(PageRequest page) {
        Page<RoomDTO> rooms = roomRepository.findAllProjectedBy(page, RoomDTO.class);
        return new ObjectPagedList<>(rooms.getContent(), rooms.getPageable(), rooms.getTotalElements());
    }

    @Override
    public Room getRoomInformation(UUID roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new NoSuchElementException("Room not found"));
    }

    @Override
    public UUID createRoom(RoomForm roomForm) {
        Room room = roomMapper.roomFormToRoom(roomForm);

        Room savedRoom = roomRepository.saveAndFlush(room);
        User admin = userRepository.findById(roomForm.creatorId()).orElseThrow(() -> new NoSuchElementException("User not found"));

        // setting up admin role for the creator
        Pair<String, List<String>> role = RoleHandler.getRoleAndPermission(DefaultRole.ADMIN);
        log.debug("Role: {}", role);
        com.chatapp.chatservice.domain.Role adminRole = roleService.updateRole(role.a, role.b);
        Member adminMember = memberRepository.saveAndFlush(
                Member
                        .builder()
                        .member(admin)
                        .memberName(admin.getUsername())
                        .room(savedRoom)
                        .role(adminRole)
                        .build());
        savedRoom.addMember(adminMember);
        return savedRoom.getRoomId();
    }

    @Override
    public UUID deleteRoom(UUID roomId, UUID memberId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NoSuchElementException("Room not found"));

        if (memberRepository.existsByRoles_RoleNameInAndRoom_RoomId(Set.of(DefaultRole.ADMIN.getLabel()), roomId)) {
            roomRepository.delete(room);
            return roomId;
        }
        throw new NoSuchElementException("Possible admin not found");
    }

    @Override
    public UUID updateRoom(UUID roomId, RoomUpdateForm roomForm) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NoSuchElementException("Room not found"));
        if (memberRepository.existsByRoles_RoleNameInAndRoom_RoomId(Set.of(DefaultRole.ADMIN.getLabel()), roomId)) {
            roomMapper.updateRoom(roomForm, room);
        }
        throw new NoSuchElementException("Possible admin not found");
    }


    // TODO
    @Override
    public void requestJoinRoom(UUID roomId) {

    }

    @Override
    public UUID handleJoinRoom(UUID roomId, UUID userId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NoSuchElementException("Room not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Member member = Member.builder()
                .member(user)
                .memberName(user.getUsername())
                .room(room)
                .build();
        room.addMember(member);
        return roomRepository.save(room).getRoomId();
    }

    @Override
    public ObjectPagedList<RoomDTO> getRoomsJoinedByUser(UUID userId, PageRequest page) {
        Page<RoomDTO> rooms = roomRepository.findAllByMembers_Member_userId(page, RoomDTO.class, userId);
        return new ObjectPagedList<>(rooms.getContent(), rooms.getPageable(), rooms.getTotalElements());
    }

    @Override
    public ObjectPagedList<RoomDTO> getRoomsNotJoinedByUser(UUID userId, PageRequest page) {
        Page<RoomDTO> rooms = roomRepository.findAllByMembers_Member_userIdNot(page, RoomDTO.class, userId);
        return new ObjectPagedList<>(rooms.getContent(), rooms.getPageable(), rooms.getTotalElements());
    }
}
