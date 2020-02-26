package ru.itis.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.models.Item;

@Data
@Builder
public class ItemDto {
    private String name;
    private int price;
    private String link;
    private String description;

    public static ItemDto from(Item item) {
        return ItemDto.builder()
                .name(item.getName())
                .price(item.getPrice())
                .link(item.getLink())
                .build();
    }
}
