package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.UserDto;
import ru.itis.dto.WishListDto;
import ru.itis.models.User;
import ru.itis.services.UserService;
import ru.itis.services.WishListService;

import java.util.Optional;

import static ru.itis.dto.WishListDto.from;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private WishListService wishListService;
    private UserService userService;

    @Autowired
    public ProfileController(WishListService wishListService, UserService userService) {
        this.wishListService = wishListService;
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("View user profile page")
    public ResponseEntity<?> getProfilePage(@RequestHeader(name = "AUTH") String token) {
        Optional<User> userCandidate = userService.findUserByToken(token);
        if (userCandidate.isPresent()) {
            UserDto userDto = UserDto.from(userCandidate.get());
            return ResponseEntity.ok(userDto);
        } else {
            throw new IllegalArgumentException("Can not find such user");
        }
    }

    @CrossOrigin
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Create new wish list")
    public ResponseEntity<?> createNewWL(@RequestParam String title, @RequestHeader("AUTH") String token) {
        WishListDto newWL = from(wishListService.addNewWL(title, token));
        return ResponseEntity.ok(newWL);
    }

    @CrossOrigin
    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete a wish list")
    public ResponseEntity<?> deleteWishList(@RequestParam String title, @RequestHeader("AUTH") String token) {
        wishListService.removeByTitle(title, token);
        return ResponseEntity.ok().build();
    }
}
