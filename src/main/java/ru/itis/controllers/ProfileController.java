package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<?> getProfilePage(@RequestHeader(name = "Authorization") String token, Authentication authentication) {
        Optional<User> userCandidate = userService.findUserByLogin(authentication.getName());
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
    public ResponseEntity<?> createNewWL(@RequestParam String title, @RequestHeader("Authorization") String token,
                                         Authentication authentication) {
        String login = authentication.getName();
        return ResponseEntity.ok(wishListService.addNewWishList(title, login));
    }

    @CrossOrigin
    @DeleteMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete a wish list")
    public Object deleteWishList(@RequestParam String title, @RequestHeader("Authorization") String token,
                                 Authentication authentication) {
        String login = authentication.getName();
        if (!wishListService.removeByTitle(title, login)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wishListService.findAllByAuthor(login));
    }
}
