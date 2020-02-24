package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.forms.UserForm;
import ru.itis.services.UserService;

import javax.validation.Valid;

@RestController
public class SignUpController {

    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping("/signUp")
    @PreAuthorize("permitAll()")
    @ApiOperation("Sign up a new user")
    public void signUpNewUser(@Valid @RequestBody UserForm userForm) {
        userService.addUser(userForm);
    }
}
