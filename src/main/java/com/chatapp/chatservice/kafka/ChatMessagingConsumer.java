package com.chatapp.chatservice.kafka;

import com.chatapp.chatservice.web.dto.MessageForm;

public interface ChatMessagingConsumer {

    public void consume(MessageForm message);
}
