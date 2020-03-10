package ru.itis.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.TokenDto;
import ru.itis.dto.UserDto;
import ru.itis.service.UserService;

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
    @ApiOperation("Login an existing user")
    public TokenDto login(@RequestBody UserDto userDto) {
        return userService.login(userDto);
    }
}
