package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.models.User;
import ru.itis.services.UserService;
import ru.itis.services.WishListService;

import javax.servlet.http.HttpServletResponse;
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
    public Object getProfilePage(@RequestHeader(name = "Authorization") String token) {
        Optional<User> userCandidate = userService.findUserByToken(token);
        if (userCandidate.isPresent()) {
            return userService.userToMap(userCandidate.get());
        } else {
            return HttpServletResponse.SC_NOT_FOUND;
        }
    }

    @CrossOrigin
    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Create new wish list")
    public Object createNewWL(@RequestParam String title, @RequestHeader("Authorization") String token) {
        return wishListService.addNewWishList(title, token);
    }

    @CrossOrigin
    @DeleteMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete a wish list")
    public Object deleteWishList(@RequestParam String title, @RequestHeader("Authorization") String token) {
        if (!wishListService.removeByTitle(title, token)) {
            return HttpServletResponse.SC_NOT_FOUND;
        }
        return wishListService.findAllByToken(token);
    }
}
