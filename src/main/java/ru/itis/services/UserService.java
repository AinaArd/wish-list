package ru.itis.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.TokenDto;
import ru.itis.dto.UserDto;
import ru.itis.models.Role;
import ru.itis.models.User;
import ru.itis.repositories.TokensRepository;
import ru.itis.repositories.UsersRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private TokensRepository tokensRepository;

    @Value("jwt.secret")
    private  String key;

    @Value("${token.expired}")
    private Integer expiredSecondsForToken;

    @Autowired
    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, TokensRepository tokensRepository) {
        this.usersRepository = usersRepository;
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
        usersRepository.save(newUser);
    }

//    public TokenDto login(UserDto userDto) {
//        Optional<User> userCandidate = usersRepository.findByLogin(userDto.getLogin());
//        String value = UUID.randomUUID().toString();
//        Token token = Token.builder()
//                .createdAt(LocalDateTime.now())
//                .expiredDateTime(LocalDateTime.now().plusSeconds(expiredSecondsForToken))
//                .value(value)
//                .build();
//        if (userCandidate.isPresent()) {
//            User user = userCandidate.get();
//            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
//                token.setUser(user);
//                tokensRepository.save(token);
//                return TokenDto.from(token);
//            }
//        } else {
//            User defaultUser = User.getDefaultUser();
//            token.setUser(defaultUser);
//            return TokenDto.from(token);
//        }
//        return null;
//    }

    public String createToken(User user) {
        return Jwts.builder()
                .claim("login", user.getLogin())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Optional<User> findUserByToken(String token) {
        String userLogin = tokensRepository.findUsernameByValue(token);
        return usersRepository.findByLogin(userLogin);
    }

    public TokenDto login(UserDto userDto) {
        Optional<User> userCandidate = usersRepository.findByLogin(userDto.getLogin());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            return new TokenDto(createToken(user));
        }
        throw new IllegalArgumentException("Can not find such user");
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
}
