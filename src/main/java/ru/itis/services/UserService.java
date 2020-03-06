package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.TokenDto;
import ru.itis.dto.UserDto;
import ru.itis.models.Role;
import ru.itis.models.Token;
import ru.itis.models.User;
import ru.itis.repositories.TokensRepository;
import ru.itis.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokensRepository tokensRepository;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokensRepository tokensRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokensRepository = tokensRepository;
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

    public TokenDto login(UserDto userDto) {
        Optional<User> userCandidate = userRepository.findByLogin(userDto.getLogin());
        String value = UUID.randomUUID().toString();
        long expiredSecondsForToken = 14L;
        Token token = Token.builder()
                .createdAt(LocalDateTime.now())
                .expiredDateTime(LocalDateTime.now().plusSeconds(expiredSecondsForToken))
                .value(value)
                .build();
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                token.setUser(user);
                tokensRepository.save(token);
                return TokenDto.from(token);
            }
        } else {
            User defaultUser = User.getDefaultUser();
            token.setUser(defaultUser);
            return TokenDto.from(token);
        }
        return null;
    }

    public Optional<User> findUserByToken(String token) {
        String userLogin = tokensRepository.findUsernameByValue(token);
        return userRepository.findByLogin(userLogin);
    }

    public List<UserDto> getUsersByLogin(String login) {
        return userRepository.findAllByLoginIsContaining(login);
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
