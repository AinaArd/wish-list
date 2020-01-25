package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.models.WishList;
import ru.itis.services.WishListService;

@Controller
public class CreateListController {

    @Autowired
    private WishListService wishListService;

    @CrossOrigin
    @PostMapping("/lists")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Create new wish list")
    public ResponseEntity<Object> createNewWL(@RequestParam String title, @RequestHeader("AUTH") String token) {
        WishList newWL = wishListService.addNewWL(title, token);
        return ResponseEntity.ok(newWL);
    }
}
