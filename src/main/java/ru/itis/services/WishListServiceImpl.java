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
public class WishListServiceImpl implements WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private TokensRepository tokensRepository;

    @Override
    public WishList addNewWL(String title, String token) {
        Optional<Token> tokenCandidate = tokensRepository.findByValue(token);
        User currentUser = tokenCandidate.orElseThrow(IllegalAccessError::new).getUser();
        WishList newWL = WishList.builder()
                .title(title)
                .author(currentUser)
                .build();
        currentUser.getWishLists().add(newWL);
        return wishListRepository.save(newWL);
    }
}
