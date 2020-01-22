package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.forms.UserForm;
import ru.itis.services.UserService;

@Controller
public class SignUpController {

    @Autowired
    public UserService userService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUpNewUser(UserForm userForm) {
        userService.addUser(userForm);
        return "redirect:login";
    }
}
