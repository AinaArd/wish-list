package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.models.Token;

import java.util.Optional;

public interface TokensRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String value);

    @Query(nativeQuery = true, value = "select u.login from token inner join \"user\" u on token.user_id = u.id where value =?")
    String findUsernameByValue(String value);
}
