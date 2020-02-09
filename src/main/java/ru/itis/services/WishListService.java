package ru.itis.services;

import ru.itis.models.WishList;

import java.util.Optional;

public interface WishListService {

    WishList addNewWL(String title, String token);

    Optional<WishList> findWishListById(Long wishListId);

    void remove(String title, String token);
}
