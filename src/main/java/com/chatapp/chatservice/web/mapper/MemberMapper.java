package com.chatapp.chatservice.web.mapper;

import com.chatapp.chatservice.domain.Member;
import com.chatapp.chatservice.domain.Room;
import com.chatapp.chatservice.web.dto.MemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {

    public static final MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);


    @Mapping(target = "username", source = "memberName")
    @Mapping(target = "pictureUrl", source = "member.pictureUrl")
    @Mapping(target = "roomId", source = "room.roomId")
    MemberDTO toMemberDto(Member member);

    List<MemberDTO> toMemberDTOList(List<Member> memberList);

    default MemberDTO fromUserAndMember(Room room, UUID userId) {
        // check that the created id matches with the member id
        Optional<Member> optionalMember = room
                .getMembers()
                .stream()
                .filter(currMember ->
                        Objects.equals(
                                currMember.getMember().getUserId(),
                                userId))
                .findFirst();
        return optionalMember.map(this::toMemberDto).orElse(null);
    }
}
