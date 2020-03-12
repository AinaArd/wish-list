package ru.itis.dto;

import lombok.Data;
import ru.itis.model.Item;

import java.util.List;

@Data
public class WishListDto {
    private String title;
    private String author;
    private List<Item> items;

    public WishListDto(String title) {
        this.title = title;
    }
}
