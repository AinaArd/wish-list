package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.forms.UserForm;
import ru.itis.services.UserService;

@Controller
@RequestMapping(path = "/signUp")
public class SignUpController {

    @Autowired
    public UserService userService;

    @CrossOrigin
    @PostMapping
    @PreAuthorize("permitAll()")
    public void signUpNewUser(@RequestBody UserForm userForm) {
        userService.addUser(userForm);
    }
}
