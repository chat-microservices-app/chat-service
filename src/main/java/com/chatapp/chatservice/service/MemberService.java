package com.chatapp.chatservice.service;

import com.chatapp.chatservice.web.dto.MemberDTO;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MemberService {

    ObjectPagedList<MemberDTO> getRoomMembers(UUID roomId, Pageable pageRequest);
}
