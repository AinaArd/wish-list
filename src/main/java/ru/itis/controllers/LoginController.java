package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.dto.TokenDto;
import ru.itis.forms.UserForm;
import ru.itis.services.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenDto> login(@RequestBody UserForm userForm) {
        return ResponseEntity.ok(userService.login(userForm));
    }
}
