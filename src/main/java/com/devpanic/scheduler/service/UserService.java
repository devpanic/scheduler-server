package com.devpanic.scheduler.service;

import com.devpanic.scheduler.dto.user.CreateUserDTO;
import com.devpanic.scheduler.entity.User;
import com.devpanic.scheduler.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(CreateUserDTO createUserDTO) {
        User newUser = User.builder()
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .build();
        return userRepository.save(newUser);
    }
}
