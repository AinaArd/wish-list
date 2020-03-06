package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> getProfilePage(@RequestHeader(name = "Authorization") String token) {
        Optional<User> userCandidate = userService.findUserByToken(token);
        if (userCandidate.isPresent()) {
            return ResponseEntity.ok(userService.userToMap(userCandidate.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Create new wish list")
    public ResponseEntity<?> createNewWL(@RequestParam String title, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(wishListService.addNewWishList(title, token));
    }

    @CrossOrigin
    @DeleteMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete a wish list")
    public Object deleteWishList(@RequestParam String title, @RequestHeader("Authorization") String token) {
        if (!wishListService.removeByTitle(title, token)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wishListService.findAllByAuthorToken(token));
    }
}
