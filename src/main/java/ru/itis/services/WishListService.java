package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.User;
import ru.itis.models.WishList;
import ru.itis.repositories.WishListRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WishListService {

    private WishListRepository wishListRepository;
    private UserService userService;

    @Autowired
    public WishListService(WishListRepository wishListRepository, UserService userService) {
        this.wishListRepository = wishListRepository;
        this.userService = userService;
    }

    public WishList addNewWishList(String title, String token) {
        Optional<User> user = userService.findUserByToken(token);
        WishList newWishList = new WishList(title, user.get());
        wishListRepository.save(newWishList);
        return newWishList;
    }

    public Optional<WishList> findWishListById(Long wishListId) {
        return wishListRepository.findById(wishListId);
    }

    public boolean removeByTitle(String title, String token) {
        Optional<WishList> wishListCandidate = wishListRepository.findByTitle(title);
        if (wishListCandidate.isPresent()) {
            wishListRepository.delete(wishListCandidate.get());
            return true;
        }
        return false;
    }

    public List<WishList> findAllByAuthorToken(String token) {
        Optional<User> user = userService.findUserByToken(token);
        return user.get().getWishLists();
    }
}
