package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.UserDto;
import ru.itis.forms.ItemForm;
import ru.itis.models.User;
import ru.itis.services.ItemService;
import ru.itis.services.UserService;

@RequestMapping("/profile")
@Controller
public class ProfileController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("View user profile page")
    public ResponseEntity<UserDto> getProfilePage(@RequestHeader(name = "AUTH") String token) {
        User currentUser = userService.findUserByToken(token).orElseThrow(IllegalArgumentException::new);
        UserDto userDto = UserDto.from(currentUser);
        return ResponseEntity.ok(userDto);
    }

    @CrossOrigin
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Add new item to wish list")
    public ResponseEntity<Object> addNewItem(ItemForm itemForm) {
        return ResponseEntity.ok(itemService.addNewItem(itemForm));
    }

    @CrossOrigin
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete an item")
    public ResponseEntity<Object> deleteItem(@RequestParam(name = "title") String itemName) {
        itemService.remove(itemName);
        return ResponseEntity.ok().build();
    }
}
