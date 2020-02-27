package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.User;
import ru.itis.models.WishList;
import ru.itis.repositories.WishListRepository;

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
        if (userService.findUserByToken(token).isPresent()) {
            User currentUser = userService.findUserByToken(token).get();
            WishList newWL = WishList.builder()
                    .title(title)
                    .author(currentUser)
                    .build();
            currentUser.getWishLists().add(newWL);
            return wishListRepository.save(newWL);
        }
        return WishList.getDefault();
    }

    public Optional<WishList> findWishListById(Long wishListId) {
        return wishListRepository.findById(wishListId);
    }

    public boolean removeByTitle(String title, String token) {
        Optional<WishList> wishListCandidate = wishListRepository.findByTitle(title);
        if (wishListCandidate.isPresent()) {
            if (userService.findUserByToken(token).isPresent()) {
                User currentUser = userService.findUserByToken(token).get();
                currentUser.getWishLists().remove(wishListCandidate.get());
                userService.save(currentUser);
                wishListRepository.delete(wishListCandidate.get());
            }
            return true;
        } else {
            return false;
        }
    }
}
