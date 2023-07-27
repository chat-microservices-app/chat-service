package com.chatapp.chatservice.web.mapper;


import com.chatapp.chatservice.domain.Message;
import com.chatapp.chatservice.web.dto.MessageDTO;
import com.chatapp.chatservice.web.dto.MessageForm;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MemberMapper.class})
public interface MessageMapper {

    Logger log = Logger.getLogger(MessageMapper.class.getName());

    @Mapping(target = "userId", source = "createdBy.userId")
    @Mapping(target = "roomId", source = "room.roomId")
    @Mapping(target ="createdAt", expression = "java(toOffsetDateTime(message.getCreatedAt()))")
    @Mapping(target ="updatedAt", expression = "java(toOffsetDateTime(message.getUpdatedAt()))")
    MessageForm toMessageForm(Message message);


    @Mapping(target ="messageId", ignore = true)
    @Mapping(target ="createdAt", ignore = true)
    @Mapping(target ="updatedAt", ignore = true)
    void updateMessageFromMessageForm(MessageForm messageForm, @MappingTarget Message message);


    @Mapping(target = "messageData", expression = "java(toMessageForm(message))")
    @Mapping(target = "userData",
            expression = "java(" +
                    "MemberMapper.INSTANCE.fromUserAndMember(message.getRoom(), message.getCreatedBy().getUserId()))")
    MessageDTO toMessageDTO(Message message);


    List<MessageDTO> toMessageDTOList(List<Message> messageList);


    @Mapping(target = "createdAt", expression = "java(toDate(messageForm.createdAt()))")
    @Mapping(target = "updatedAt", expression = "java(toDate(messageForm.updatedAt()))")
    Message toMessage(MessageForm messageForm);


    default Date toDate(OffsetDateTime offsetDateTime) {
        return Date.from(offsetDateTime.toInstant());
    }
    default OffsetDateTime toOffsetDateTime(Date date) {
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneOffset.systemDefault());
    }


}
