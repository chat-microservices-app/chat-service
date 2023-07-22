package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.Member;
import com.chatapp.chatservice.repository.MemberRepository;
import com.chatapp.chatservice.web.dto.MemberDTO;
import com.chatapp.chatservice.web.mapper.MemberMapper;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

;


@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    private final MemberRepository memberRepository;

    @Override
    public ObjectPagedList<MemberDTO> getRoomMembers(UUID roomId, Pageable pageRequest) {
        Page<Member> memberPage = memberRepository.findByRoom_RoomId(roomId, pageRequest);
        return new ObjectPagedList<>(
                memberMapper.toMemberDTOList(memberPage.getContent()),
                memberPage.getPageable(),
                memberPage.getTotalElements()
        );
    }
}
