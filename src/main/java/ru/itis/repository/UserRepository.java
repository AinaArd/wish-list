package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dto.UserDto;
import ru.itis.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    List<UserDto> findAllByLoginIsContaining(String login);
}
