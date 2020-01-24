package ru.itis.services;

import org.springframework.security.core.Authentication;
import ru.itis.dto.TokenDto;
import ru.itis.forms.UserForm;
import ru.itis.models.User;

public interface UserService {
    void addUser(UserForm userForm);

    User getCurrentUser(Authentication authentication);

    TokenDto login(UserForm userForm);
}
