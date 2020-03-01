package ru.itis.dto;

import lombok.Data;

@Data
public class ItemDto {
    private String name;
    private int price;
    private String link;
    private String description;
}
