package com.chatapp.chatservice.kafka;


import com.chatapp.chatservice.web.dto.UserDTO;

public interface UserManagementConsumer {
    void consume(UserDTO message);


    void consumeDelete(UserDTO message);
}
