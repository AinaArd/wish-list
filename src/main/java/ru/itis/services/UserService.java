package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.TokenDto;
import ru.itis.dto.UserDto;
import ru.itis.forms.UserForm;
import ru.itis.models.Role;
import ru.itis.models.Token;
import ru.itis.models.User;
import ru.itis.repositories.TokensRepository;
import ru.itis.repositories.UsersRepository;
import ru.itis.security.details.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private TokensRepository tokensRepository;

    @Autowired
    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, TokensRepository tokensRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokensRepository = tokensRepository;
    }

    public void addUser(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        User newUser = User.builder()
                .login(userForm.getLogin())
                .password(hashPassword)
                .role(Role.AUTHOR)
                .build();
        usersRepository.save(newUser);
    }

    public User getCurrentUser(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
    }

    @Value("${token.expired}")
    private Integer expiredSecondsForToken;

    public TokenDto login(UserForm userForm) {
        Optional<User> userCandidate = usersRepository.findByLogin(userForm.getLogin());
        String value = UUID.randomUUID().toString();
        Token token = Token.builder()
                .createdAt(LocalDateTime.now())
                .expiredDateTime(LocalDateTime.now().plusSeconds(expiredSecondsForToken))
                .value(value)
                .build();
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (passwordEncoder.matches(userForm.getPassword(), user.getPassword())) {
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
        return usersRepository.findByLogin(userLogin);
    }

    public List<UserDto> getUsersByName(String login) {
        return usersRepository.findAllByLoginIsContaining(login);
    }
}
