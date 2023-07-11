package com.chatapp.chatservice.service;

import com.chatapp.chatservice.web.dto.MessageForm;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface MessageService {

    MessageForm saveMessage(MessageForm messageForm);

    ObjectPagedList<MessageForm> getMessagesByRoomId(UUID roomId, PageRequest pageRequest);


}
