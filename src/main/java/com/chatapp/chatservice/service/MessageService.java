package com.chatapp.chatservice.service;

import com.chatapp.chatservice.web.dto.MessageDTO;
import com.chatapp.chatservice.web.dto.MessageForm;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface MessageService {

    MessageDTO saveMessage(MessageForm messageForm);

    ObjectPagedList<MessageDTO> getMessagesByRoomId(UUID roomId, PageRequest pageRequest);


}
