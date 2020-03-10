package ru.itis.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ItemDto;
import ru.itis.model.WishList;
import ru.itis.service.ItemService;
import ru.itis.service.WishListService;

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
    @GetMapping("/lists/{listId}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Get a single wish list")
    public ResponseEntity<?> getWishList(@PathVariable Long listId, @RequestHeader(name = "Authorization") String token) {
        WishList defaultWishList = WishList.getDefault();
        return ResponseEntity.ok(wishListService.findWishListById(listId).orElse(defaultWishList));
    }

    @CrossOrigin
    @PostMapping("/lists/{listId}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Add new item to wish list")
    public void addNewItem(@PathVariable Long listId, ItemDto itemDto, @RequestHeader(name = "Authorization") String token) {
        itemService.addNewItem(itemDto, listId);
    }

    @CrossOrigin
    @DeleteMapping("/lists/{listId}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Delete an item")
    public void deleteItem(@PathVariable Long listId, @RequestParam("title") String itemName,
                           @RequestHeader(name = "Authorization") String token) {
        itemService.removeByName(itemName);
    }
}
