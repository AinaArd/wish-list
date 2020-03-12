package ru.itis.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.service.UserService;

@RestController
public class SearchController {

    private UserService userService;

    @Autowired
    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PreAuthorize("permitAll()")
    @PostMapping("/search")
    @ApiOperation("Find users by name")
    public ResponseEntity<?> findUsers(@RequestParam String login) {
        return ResponseEntity.ok(userService.getUsersByLogin(login));
    }
}
