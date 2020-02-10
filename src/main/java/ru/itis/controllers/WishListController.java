package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.WishListDto;
import ru.itis.forms.ItemForm;
import ru.itis.models.WishList;
import ru.itis.services.ItemService;
import ru.itis.services.WishListService;

import java.util.Optional;

import static ru.itis.dto.WishListDto.from;

@Controller
public class WishListController {

    @Autowired
    public ItemService itemService;

    @Autowired
    public WishListService wishListService;

    @CrossOrigin
    @GetMapping("/lists/{list-id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Get a single wish list")
    public ResponseEntity<Object> getWishListPage(@PathVariable(name = "list-id") Long listId) {
        Optional<WishList> wishListCandidate = wishListService.findWishListById(listId);
        if (wishListCandidate.isPresent()) {
            WishListDto wishListDto = from(wishListCandidate.get());
            return ResponseEntity.ok(wishListDto);
        } else throw new IllegalArgumentException("Can not find such wish list");
    }

    @CrossOrigin
    @PostMapping("/lists/{list-id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Add new item to wish list")
    public ResponseEntity<Object> addNewItem(@PathVariable(name = "list-id") Long listId, ItemForm itemForm) {
        return ResponseEntity.ok(itemService.addNewItem(itemForm, listId));
    }

    @CrossOrigin
    @DeleteMapping("/lists/{list-id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete an item")
    public ResponseEntity<Object> deleteItem(@PathVariable(name = "list-id") Long listId ,@RequestParam(name = "title") String itemName) {
        itemService.removeByName(itemName, listId);
        return ResponseEntity.ok().build();
    }
}
