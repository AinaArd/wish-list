package ru.itis.dto;

import lombok.Data;
import ru.itis.models.WishList;

@Data
public class ItemDto {
    private String name;
    private int price;
    private String link;
    private String description;
    private WishList wishList;
}
