package ru.itis.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.TokenDto;
import ru.itis.dto.UserDto;
import ru.itis.models.Role;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

import java.util.*;

@Service
public class UserService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private static final String KEY = "secret";

    @Autowired
    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(UserDto userDto) {
        String hashPassword = passwordEncoder.encode(userDto.getPassword());
        User newUser = User.builder()
                .login(userDto.getLogin())
                .password(hashPassword)
                .role(Role.AUTHOR)
                .build();
        usersRepository.save(newUser);
    }

    private String createToken(User user) {
        return Jwts.builder()
                .claim("login", user.getLogin())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

    public TokenDto login(UserDto userDto) {
        Optional<User> userCandidate = usersRepository.findByLogin(userDto.getLogin());
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            return new TokenDto(createToken(user));
        }
        throw new NoSuchElementException("Can not find such user");
    }

    public List<UserDto> getUsersByLogin(String login) {
        return usersRepository.findAllByLoginIsContaining(login);
    }

    public Map<String, Object> userToMap(User user) {
        Map<String, Object> userProperties = new HashMap<>();
        userProperties.put("login", user.getLogin());
        userProperties.put("wishLists", user.getWishLists());
        return userProperties;
    }

    public Optional<User> findUserByLogin(String login) {
        return usersRepository.findByLogin(login);
    }
}
