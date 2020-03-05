package ru.itis.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UsersRepository usersRepository;

    @Autowired
    public JwtUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByLogin(login);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }
        return new UserDetailsImpl(user.get());
    }
}