package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;

@Controller
public class ProfileController {

    @CrossOrigin
    @ApiOperation("Get user profile")
    @GetMapping("/userProfile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getUserProfile(Authentication authentication){
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        User user = details.getUser();
        System.out.println(user.getLogin());
        return ResponseEntity.ok(user);
    }
}
