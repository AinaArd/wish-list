package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.model.User;
import ru.itis.model.WishList;
import ru.itis.repository.WishListRepository;

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

    public WishList addNewWishList(String title, String login) {
        Optional<User> user = userService.findUserByLogin(login);
        WishList newWishList = new WishList(title, user.get());
        wishListRepository.save(newWishList);
        return newWishList;
    }

    public Optional<WishList> findWishListById(Long wishListId) {
        return wishListRepository.findById(wishListId);
    }

    public boolean removeByTitle(String title, String login) {
        Optional<WishList> wishListCandidate = wishListRepository.findByTitle(title);
        User author = userService.findUserByLogin(login).get();
        if (wishListCandidate.isPresent()) {
            wishListRepository.delete(wishListCandidate.get());
            author.getWishLists().remove(wishListCandidate.get());
            return true;
        }
        return false;
    }

    public List<WishList> findAllByAuthor(String login) {
        Optional<User> user = userService.findUserByLogin(login);
        return user.get().getWishLists();
    }
}
