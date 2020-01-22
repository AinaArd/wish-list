package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.WishList;

public interface WishListRepository extends JpaRepository<WishList, Long> {

}
