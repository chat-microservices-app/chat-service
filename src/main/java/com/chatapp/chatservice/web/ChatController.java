package com.chatapp.chatservice.web;


import com.chatapp.chatservice.annotation.CreateMessagePerm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/chats")
@RestController
public class ChatController {

    @GetMapping(produces = "application/json")
    @CreateMessagePerm
    public String getChats() {
        return "Hello from chat service";
    }
}
