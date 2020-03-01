package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Item;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListDto {
    private String title;
    private String author;
    private List<Item> items;
}
