package com.devpanic.scheduler.controller;

import com.devpanic.scheduler.dto.user.CreateUserDTO;
import com.devpanic.scheduler.entity.User;
import com.devpanic.scheduler.service.UserService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            User createUser = userService.createUser(createUserDTO);
            return ResponseEntity
                    .created(new URI("/api/users/" + createUser.getId()))
                    .body(createUser);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
