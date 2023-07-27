package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.Message;
import com.chatapp.chatservice.repository.MessageRepository;
import com.chatapp.chatservice.repository.RoomRepository;
import com.chatapp.chatservice.repository.UserRepository;
import com.chatapp.chatservice.web.dto.MessageDTO;
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
import java.util.NoSuchElementException;
import java.util.Optional;
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
    public MessageDTO saveMessage(MessageForm messageForm) {
        Message newMessage = Message
                .builder()
                .message(messageForm.message())
                .room(roomRepository.getReferenceById(messageForm.roomId()))
                .createdBy(userRepository.getReferenceById(messageForm.userId()))
                .build();
        return messageMapper.toMessageDTO(messageRepository.saveAndFlush(newMessage));
    }

    @Override
    public UUID deleteMessage(UUID messageId, UUID userId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (!userRepository.existsById(userId) || optionalMessage.isEmpty()) {
            throw new NoSuchElementException("user or message not found");
        }

        Message message = optionalMessage.get();
        if (message.getCreatedBy().getUserId().equals(userId)) {
            messageRepository.deleteById(messageId);
            return messageId;
        }
        throw new NoSuchElementException("user not authorized to delete this message");

    }

    @Override
    public MessageDTO updateMessage(MessageForm messageForm) {
        Optional<Message> optionalMessage = messageRepository.findById(messageForm.messageId());
        if (!userRepository.existsById(messageForm.userId()) || optionalMessage.isEmpty()) {
            throw new NoSuchElementException("user id or message not found");
        }

        Message message = optionalMessage.get();
        if (message.getCreatedBy().getUserId().equals(messageForm.userId())) {
            messageMapper.updateMessageFromMessageForm(messageForm, message);
            return messageMapper.toMessageDTO(messageRepository.saveAndFlush(message));
        }
        throw new IllegalArgumentException("user not authorized to update this message");
    }

    @Override
    public ObjectPagedList<MessageDTO> getMessagesByRoomId(UUID roomId, PageRequest pageRequest) {
        Page<Message> messagePage = messageRepository.findByRoom_RoomId(roomId, pageRequest, Message.class);
        List<MessageDTO> messageFormList = messageMapper.toMessageDTOList(messagePage.getContent());
        return new ObjectPagedList<>(
                messageFormList,
                messagePage.getPageable(),
                messagePage.getTotalElements()
        );
    }


}
