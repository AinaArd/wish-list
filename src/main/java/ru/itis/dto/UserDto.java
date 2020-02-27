package ru.itis.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.models.User;
import ru.itis.models.WishList;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String login;
    private List<WishList> wishLists;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .wishLists(user.getWishLists())
                .build();
    }
}
