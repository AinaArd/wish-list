package ru.itis.services;

import ru.itis.models.WishList;

import java.util.Optional;

public interface WishListService {

    WishList addNewWL(String title, String token);

    Optional<WishList> findWishListById(Long wishListId);

    void removeByTitle(String title, String token);
}
