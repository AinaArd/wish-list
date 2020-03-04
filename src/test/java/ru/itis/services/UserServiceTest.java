package ru.itis.services;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dto.TokenDto;
import ru.itis.dto.UserDto;
import ru.itis.models.User;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class UserServiceTest {
    private static UserDto userDto = new UserDto("test", "test1");

    @Autowired
    private UserService userService;

    @Test
    public void addUserWorksGood() {
        Optional<User> found = userService.findUserByLogin(userDto.getLogin());
        found.ifPresent(user -> assertEquals(user.getLogin(), userDto.getLogin()));
    }

    @Test
    public void loginUserWorksGood() {
        TokenDto token = userService.login(userDto);
        assertNotNull(token.getValue());
    }
}