package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.Message;
import com.chatapp.chatservice.repository.MessageRepository;
import com.chatapp.chatservice.repository.RoomRepository;
import com.chatapp.chatservice.repository.UserRepository;
import com.chatapp.chatservice.web.dto.MessageForm;
import com.chatapp.chatservice.web.mapper.MessageMapper;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageForm saveMessage(MessageForm messageForm) {
        Message newMessage = Message
                .builder()
                .message(messageForm.message())
                .room(roomRepository.getReferenceById(messageForm.roomId()))
                .createdBy(userRepository.getReferenceById(messageForm.userId()))
                .build();
        return messageMapper.toMessageForm(messageRepository.saveAndFlush(newMessage));
    }

    @Override
    public ObjectPagedList<MessageForm> getMessagesByRoomId(UUID roomId, PageRequest pageRequest) {
        Page<Message> messagePage = messageRepository.findByRoom_RoomId(roomId, pageRequest, Message.class);
        List<MessageForm>  messageFormList = messageMapper.toMessageFormList(messagePage.getContent());
        return new ObjectPagedList<>(
                messageFormList,
                messagePage.getPageable(),
                messagePage.getTotalElements()
        );
    }


}
