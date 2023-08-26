package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.User;
import com.chatapp.chatservice.repository.UserRepository;
import com.chatapp.chatservice.web.dto.UserDTO;
import com.chatapp.chatservice.web.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public void updateUser(UserDTO userToUpdate) {
        User user = userRepository.findById(userToUpdate.getUserId()).orElse(
                User.builder().userId(userToUpdate.getUserId()).build()
        );
        userMapper.updateUser(userToUpdate, user);
        userRepository.save(user);
    }

}
