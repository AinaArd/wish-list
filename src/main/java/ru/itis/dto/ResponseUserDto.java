package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.WishList;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDto {
    private String login;
    private List<WishList> wishLists;
}
