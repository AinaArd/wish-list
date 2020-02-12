package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.Token;
import ru.itis.models.User;
import ru.itis.models.WishList;
import ru.itis.repositories.TokensRepository;
import ru.itis.repositories.WishListRepository;

import java.util.Optional;

@Service
public class WishListService {

    private WishListRepository wishListRepository;
    private TokensRepository tokensRepository;

    @Autowired
    public WishListService(WishListRepository wishListRepository, TokensRepository tokensRepository) {
        this.wishListRepository = wishListRepository;
        this.tokensRepository = tokensRepository;
    }

    public WishList addNewWL(String title, String token) {
        Optional<Token> tokenCandidate = tokensRepository.findByValue(token);
        User currentUser = tokenCandidate.orElseThrow(IllegalArgumentException::new).getUser();
        WishList newWL = WishList.builder()
                .title(title)
                .author(currentUser)
                .build();
        currentUser.getWishLists().add(newWL);
        return wishListRepository.save(newWL);
    }

    public Optional<WishList> findWishListById(Long wishListId) {
        return wishListRepository.findById(wishListId);
    }

    public void removeByTitle(String title, String token) {
        Optional<WishList> wishListCandidate = wishListRepository.findByTitle(title);
        if(wishListCandidate.isPresent()) {
            wishListRepository.delete(wishListCandidate.get());
            Optional<Token> tokenCandidate = tokensRepository.findByValue(token);
            User currentUser = tokenCandidate.orElseThrow(IllegalArgumentException::new).getUser();
            currentUser.getWishLists().remove(wishListCandidate.get());
        } else {
            throw new IllegalArgumentException("Can not find such wish list");
        }
    }
}
