package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.User;
import com.chatapp.chatservice.repository.UserRepository;
import com.chatapp.chatservice.web.dto.UserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void updateUser(UserDTO userToUpdate) {
        User user = userRepository.findById(userToUpdate.getUserId()).orElse(
                User.builder().userId(userToUpdate.getUserId()).build()
        );
        UserDtoToUser(userToUpdate, user);
        userRepository.save(user);
    }


    @Deprecated
    private void UserDtoToUser(UserDTO temp, User user) {
        user.setFirstName(temp.getFirstName());
        user.setLastName(temp.getLastName());
        user.setUsername(temp.getUsername());
        user.setPictureUrl(temp.getPictureUrl());
    }
}
