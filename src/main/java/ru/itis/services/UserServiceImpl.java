package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.forms.UserForm;
import ru.itis.models.Role;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        User newUser = User.builder()
                .login(userForm.getLogin())
                .password(hashPassword)
                .role(Role.AUTHOR)
                .build();
        usersRepository.save(newUser);
    }
}
