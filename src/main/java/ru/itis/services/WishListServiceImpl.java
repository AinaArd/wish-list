package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.itis.models.User;
import ru.itis.models.WishList;
import ru.itis.repositories.WishListRepository;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private UserService userService;

    @Override
    public WishList addNewWL(String title, Authentication authentication) {
        User currentUser = userService.getCurrentUser(authentication);
        WishList newWL = WishList.builder()
                .title(title)
                .author(currentUser)
                .build();
        currentUser.getWishLists().add(newWL);
        return wishListRepository.save(newWL);
    }
}
