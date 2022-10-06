package com.epam.esm.controllers;

import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.UserProcessor;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;
    private final UserProcessor userProcessor;

    @Autowired
    public UsersController(UserService userService, UserProcessor userProcessor) {
        this.userService = userService;
        this.userProcessor = userProcessor;
    }

    @GetMapping
    public List<UserDto> findAllUsers(){
        List<UserDto> users = userService.findAll();
        users.forEach(userProcessor::process);
        return users;
    }

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable Long id){
        return userService.findById(id);
    }
}
