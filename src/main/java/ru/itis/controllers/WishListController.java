package ru.itis.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.WishListDto;
import ru.itis.forms.ItemForm;
import ru.itis.models.WishList;
import ru.itis.services.ItemService;
import ru.itis.services.WishListService;

import java.util.Optional;

@RestController
public class WishListController {

    @Autowired
    public ItemService itemService;

    @Autowired
    public WishListService wishListService;

    @CrossOrigin
    @GetMapping("/lists/{list-id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Get a single wish list")
    public WishListDto getWishList(@PathVariable Long listId) {
        Optional<WishList> wishListCandidate = wishListService.findWishListById(listId);
        if (wishListCandidate.isPresent()) {
            return WishListDto.from(wishListCandidate.get());
        } else {
            throw new IllegalArgumentException("Can not find such wish list");
        }
    }

    @CrossOrigin
    @PostMapping("/lists/{list-id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Add new item to wish list")
    public void addNewItem(@PathVariable Long listId, ItemForm itemForm) {
        itemService.addNewItem(itemForm, listId);
    }

    @CrossOrigin
    @DeleteMapping("/lists/{list-id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete an item")
    public void deleteItem(@PathVariable Long listId, @RequestParam(name = "title") String itemName) {
        itemService.removeByName(itemName);
    }
}
