package com.chatapp.chatservice.kafka;


import com.chatapp.chatservice.web.dto.UserDTO;

public interface UserManagementConsumer {

    public void consume(UserDTO message);
}
