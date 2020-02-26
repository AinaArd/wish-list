package ru.itis.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.models.Item;
import ru.itis.models.WishList;

import java.util.List;

@Data
@Builder
public class WishListDto {
    private Long id;
    private String title;
    private String author;
    private List<Item> items;

    public static WishListDto from(WishList wishList) {
        return WishListDto.builder()
                .id(wishList.getId())
                .title(wishList.getTitle())
                .author(wishList.getAuthor().getLogin())
                .items(wishList.getItems())
                .build();
    }
}
