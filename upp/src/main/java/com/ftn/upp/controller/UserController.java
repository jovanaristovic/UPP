package com.ftn.upp.controller;

import com.ftn.upp.model.User;
import com.ftn.upp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);

        return user;
    }


}
