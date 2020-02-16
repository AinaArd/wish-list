package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.Token;
import ru.itis.models.User;
import ru.itis.models.WishList;
import ru.itis.repositories.TokensRepository;
import ru.itis.repositories.WishListRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class WishListService {

    private WishListRepository wishListRepository;
    private TokensRepository tokensRepository;
    private UserService userService;

    @Autowired
    public WishListService(WishListRepository wishListRepository, TokensRepository tokensRepository,
                           UserService userService) {
        this.wishListRepository = wishListRepository;
        this.tokensRepository = tokensRepository;
        this.userService = userService;
    }

    public WishList addNewWishList(String title, String token) {
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

    public void removeByTitle(String title, String token, HttpServletResponse response) {
        Optional<WishList> wishListCandidate = wishListRepository.findByTitle(title);
        if (wishListCandidate.isPresent()) {
            User currentUser = userService.findUserByToken(token).get();
            currentUser.getWishLists().remove(wishListCandidate.get());
            userService.save(currentUser);
            wishListRepository.delete(wishListCandidate.get());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
