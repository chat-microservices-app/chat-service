package com.chatapp.chatservice.web.mapper;


import com.chatapp.chatservice.domain.Message;
import com.chatapp.chatservice.web.dto.MessageForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {



    @Mapping(target = "userId", source = "createdBy.userId")
    @Mapping(target = "roomId", source = "room.roomId")
    MessageForm toMessageForm(Message message);


    List<MessageForm> toMessageFormList(List<Message> messageList);
    Message toMessage(MessageForm messageForm);


}
