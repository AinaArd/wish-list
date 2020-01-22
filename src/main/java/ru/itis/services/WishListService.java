package ru.itis.services;

import org.springframework.security.core.Authentication;
import ru.itis.models.WishList;

public interface WishListService {
    WishList addNewWL(String title, Authentication authentication);
}
