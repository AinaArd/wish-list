package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.services.WishListService;

@Controller
public class AddWishListController {

    @Autowired
    private WishListService wishListService;

    @GetMapping("/lists")
    public String getAddNewPage() {
        return "addList";
    }

    @PostMapping("/lists")
    public String addNewWL(@RequestParam(name = "title") String wlTitle, Authentication authentication) {
        wishListService.addNewWL(wlTitle, authentication);
        return "redirect:profile";
    }
}
