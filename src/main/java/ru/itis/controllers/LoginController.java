package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.TokenDto;
import ru.itis.forms.UserForm;
import ru.itis.services.UserService;

@RestController
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public TokenDto login(@RequestBody UserForm userForm) {
        return userService.login(userForm);
    }
}
