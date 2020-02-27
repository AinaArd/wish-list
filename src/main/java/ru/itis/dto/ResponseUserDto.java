package ru.itis.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.models.User;
import ru.itis.models.WishList;

import java.util.List;

@Data
@Builder
public class ResponseUserDto {

    private String login;
    private String password;
    private List<WishList> wishLists;

    public static ResponseUserDto from(User user) {
        return ResponseUserDto.builder()
                .login(user.getLogin())
                .wishLists(user.getWishLists())
                .build();
    }
}
