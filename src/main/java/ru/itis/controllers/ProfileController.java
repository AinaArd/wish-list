package ru.itis.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.security.details.UserDetailsImpl;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String getProfilePage(Authentication authentication, ModelMap modelMap){
        if (authentication == null) {
            return "redirect:login";
        }
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
//        UserDto user = from(details.getUser());
        modelMap.addAttribute("user", details.getUser());
        return "profile";
    }
}
