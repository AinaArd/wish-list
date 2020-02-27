package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itis.models.User;
import ru.itis.models.WishList;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseUserDto {

    private String login;
    private List<WishList> wishLists;

    public static ResponseUserDto from(User user) {
        return new ResponseUserDto(user.getLogin(), user.getWishLists());
    }
}
