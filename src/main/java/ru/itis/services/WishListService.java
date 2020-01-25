package ru.itis.services;

import ru.itis.models.WishList;

public interface WishListService {

    WishList addNewWL(String title, String token);
}
