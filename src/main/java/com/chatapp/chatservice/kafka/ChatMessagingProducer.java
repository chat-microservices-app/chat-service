package com.chatapp.chatservice.kafka;

import com.chatapp.chatservice.web.dto.MessageForm;

import java.util.UUID;

public interface ChatMessagingProducer {

    void sendMessage(MessageForm messageForm);

    void deleteMessage(MessageForm messageToDelete);

    void updateMessage(MessageForm messageForm);
}
