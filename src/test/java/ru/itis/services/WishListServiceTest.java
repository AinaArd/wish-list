package ru.itis.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dto.UserDto;
import ru.itis.dto.WishListDto;
import ru.itis.models.Token;
import ru.itis.models.User;
import ru.itis.models.WishList;
import ru.itis.repositories.TokensRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishListServiceTest {
    private WishListDto wishListDto;
    private Token token;
    private UserDto user;

    @Before
    public void initialize() {
        wishListDto = new WishListDto("14 Feb");
        token = new Token();
        prepareToken(prepareUser(user));
    }

    @Autowired
    private WishListService wishListService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokensRepository tokensRepository;

    @Test
    public void addWishListWorksGood() {
        wishListService.addNewWishList(wishListDto.getTitle(), token.getValue());
        Optional<WishList> found = wishListService.findWishListByTitle(wishListDto.getTitle());
        found.ifPresent(list -> assertEquals(wishListDto.getTitle(), list.getTitle()));
    }

    private User prepareUser(UserDto userDto) {
        user = new UserDto("aina", "aina1");
        userService.addUser(userDto);
        return userService.findUserByLogin("aina").get();
    }

    private void prepareToken(User user){
        Token token = new Token(UUID.randomUUID().toString(), user, LocalDateTime.now(), LocalDateTime.now().plusDays(14L));
        tokensRepository.save(token);
    }
}
