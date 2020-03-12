package ru.itis.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.TokenDto;
import ru.itis.dto.UserDto;
import ru.itis.model.Role;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private static final String KEY = "secret";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(UserDto userDto) {
        String hashPassword = passwordEncoder.encode(userDto.getPassword());
        User newUser = User.builder()
                .login(userDto.getLogin())
                .password(hashPassword)
                .role(Role.AUTHOR)
                .build();
        userRepository.save(newUser);
    }

    private String createToken(User user) {
        return Jwts.builder()
                .claim("login", user.getLogin())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

    public TokenDto login(UserDto userDto) {
        Optional<User> userCandidate = userRepository.findByLogin(userDto.getLogin());
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            return new TokenDto(createToken(user));
        }
        throw new NoSuchElementException("Can not find such user");
    }

    public Map<String, Object> userToMap(User user) {
        Map<String, Object> userProperties = new HashMap<>();
        userProperties.put("login", user.getLogin());
        userProperties.put("wishLists", user.getWishLists());
        return userProperties;
    }

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
