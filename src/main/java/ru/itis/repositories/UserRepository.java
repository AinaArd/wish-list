package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dto.UserDto;
import ru.itis.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    List<UserDto> findAllByLoginIsContaining(String login);
}
