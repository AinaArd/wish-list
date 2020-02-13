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

    private ItemService itemService;
    private WishListService wishListService;

    @Autowired
    public WishListController(ItemService itemService, WishListService wishListService) {
        this.itemService = itemService;
        this.wishListService = wishListService;
    }

    @CrossOrigin
    @GetMapping("/lists/{list-id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Get a single wish list")
    public WishListDto getWishList(@PathVariable("list-id") Long listId, @RequestHeader(name = "AUTH") String token) {
//      TODO: if anonym --> ability to reserve an item
        Optional<WishList> wishListCandidate = wishListService.findWishListById(listId);
        if (wishListCandidate.isPresent()) {
            return WishListDto.from(wishListCandidate.get());
        } else {
            return WishListDto.from(wishListService.getDefaultWishList());
        }
    }

    @CrossOrigin
    @PostMapping("/lists/{list-id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Add new item to wish list")
    public void addNewItem(@PathVariable("list-id") Long listId, ItemForm itemForm, @RequestHeader(name = "AUTH") String token) {
        itemService.addNewItem(itemForm, listId);
    }

    @CrossOrigin
    @DeleteMapping("/lists/{list-id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete an item")
    public void deleteItem(@PathVariable("list-id") Long listId, @RequestParam("title") String itemName,
                           @RequestHeader(name = "AUTH") String token) {
        itemService.removeByName(itemName);
    }
}
