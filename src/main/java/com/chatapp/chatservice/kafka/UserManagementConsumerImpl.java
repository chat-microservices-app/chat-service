package com.chatapp.chatservice.kafka;


import com.chatapp.chatservice.service.UserService;
import com.chatapp.chatservice.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserManagementConsumerImpl implements UserManagementConsumer {

    private final UserService userService;

    @KafkaListener(topics = "${spring.kafka.topic.user-updates}",
            groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(UserDTO userDTO) {
        log.info("Consumed message: {}", userDTO);
        userService.updateUser(userDTO);
    }
}
