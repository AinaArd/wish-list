package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.UserDto;
import ru.itis.services.UserService;

import java.util.List;

@RestController
public class SearchController {

    private UserService userService;

    @Autowired
    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PreAuthorize("isAnonymous()")
    @PostMapping("/search")
    @ApiOperation("Find users by name")
    public List<UserDto> findUsers(@RequestParam String name) {
        return userService.getUsersByName(name);
    }
}
