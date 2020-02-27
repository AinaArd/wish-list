package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ResponseUserDto;
import ru.itis.dto.WishListDto;
import ru.itis.models.User;
import ru.itis.services.UserService;
import ru.itis.services.WishListService;

import java.util.Optional;

@RestController
public class ProfileController {

    private WishListService wishListService;
    private UserService userService;

    @Autowired
    public ProfileController(WishListService wishListService, UserService userService) {
        this.wishListService = wishListService;
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("View user profile page")
    public ResponseUserDto getProfilePage(@RequestHeader(name = "AUTH") String token) {
        Optional<User> userCandidate = userService.findUserByToken(token);
        if (userCandidate.isPresent()) {
            return ResponseUserDto.from(userCandidate.get());
        } else {
            User defaultUserDto  = User.getDefaultUser();
            return ResponseUserDto.from(defaultUserDto);
        }
    }

    @CrossOrigin
    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Create new wish list")
    public WishListDto createNewWL(@RequestParam String title, @RequestHeader("AUTH") String token) {
        return WishListDto.from(wishListService.addNewWishList(title, token));
    }

    @CrossOrigin
    @DeleteMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete a wish list")
    public ResponseEntity<?> deleteWishList(@RequestParam String title, @RequestHeader("AUTH") String token) {
        if(!wishListService.removeByTitle(title, token)) {
            return new ResponseEntity<>((HttpStatus.NOT_FOUND));
        }
        return ResponseEntity.ok().build();
    }
}
