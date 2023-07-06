package com.chatapp.chatservice.kafka;

import com.chatapp.chatservice.web.dto.MessageForm;

public interface ChatMessagingProducer {

    void sendMessage(MessageForm messageForm);
}
