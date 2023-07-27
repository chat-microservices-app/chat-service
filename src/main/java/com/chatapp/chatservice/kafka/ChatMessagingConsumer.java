package com.chatapp.chatservice.kafka;

import com.chatapp.chatservice.web.dto.MessageForm;
import org.antlr.v4.runtime.misc.Pair;

import java.util.UUID;

public interface ChatMessagingConsumer {

    void consume(MessageForm message);

    void consumeUpdateMessage(MessageForm message);

    void consumeDeleteMessage(MessageForm message);
}
