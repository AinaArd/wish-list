package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ResponseDto;
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
    public ResponseDto getProfilePage(@RequestHeader(name = "Authorization") String token) {
        Optional<User> userCandidate = userService.findUserByToken(token);
        return userCandidate.map(user -> new ResponseDto(HttpStatus.OK, userService.userToMap(user))).orElseGet(() ->
                new ResponseDto(HttpStatus.OK, "Invalid token"));
    }

    @CrossOrigin
    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Create new wish list")
    public ResponseDto createNewWL(@RequestParam String title, @RequestHeader("Authorization") String token) {
        return new ResponseDto(HttpStatus.OK, wishListService.addNewWishList(title, token));
    }

    @CrossOrigin
    @DeleteMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete a wish list")
    public ResponseDto deleteWishList(@RequestParam String title, @RequestHeader("Authorization") String token) {
        if (!wishListService.removeByTitle(title, token)) {
            return new ResponseDto(HttpStatus.OK, "Invalid wish list title");
        }
        return new ResponseDto(HttpStatus.OK, wishListService.findAllByAuthor(token) );
    }
}
