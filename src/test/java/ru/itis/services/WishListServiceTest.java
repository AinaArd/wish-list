package ru.itis.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dto.WishListDto;
import ru.itis.models.User;
import ru.itis.models.WishList;
import ru.itis.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishListServiceTest {
    private WishListDto wishListDto;
    private String token;
    private User user;

    @Before
    public void initialize() {
        wishListDto = new WishListDto("14 Feb");
        token = UUID.randomUUID().toString();
        user = userRepository.save(user);
    }

    @Autowired
    private WishListService wishListService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addWishListWorksOk() {
        wishListService.addNewWishList(wishListDto.getTitle(), token);
        Optional<WishList> found = wishListService.findWishListByTitle(wishListDto.getTitle());
        found.ifPresent(list -> assertEquals(wishListDto.getTitle(), list.getTitle()));
    }
}
