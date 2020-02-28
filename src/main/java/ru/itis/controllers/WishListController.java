package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ItemDto;
import ru.itis.models.WishList;
import ru.itis.services.ItemService;
import ru.itis.services.WishListService;

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
    public WishList getWishList(@PathVariable Long listId, @RequestHeader(name = "Authorization")
            String token) {
        WishList defaultWishList = WishList.getDefault();
        return wishListService.findWishListById(listId).orElse(defaultWishList);
    }

    @CrossOrigin
    @PostMapping("/lists/{listId}")
    public void addNewItem(@PathVariable Long listId, ItemDto itemDto, @RequestHeader(name = "Authorization")
            String token) {
        itemService.addNewItem(itemDto, listId);
    }

    @CrossOrigin
    @DeleteMapping("/lists/{listId}")
    public void deleteItem(@PathVariable Long listId, @RequestParam("title") String itemName,
                           @RequestHeader(name = "Authorization") String token) {
        itemService.removeByName(itemName);
    }
}
