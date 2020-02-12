package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.forms.UserForm;
import ru.itis.services.UserService;

@Controller
@RequestMapping(path = "/signUp")
public class SignUpController {

    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping
    @PreAuthorize("permitAll()")
    public void signUpNewUser(@RequestBody UserForm userForm) {
        userService.addUser(userForm);
    }
}
