package ru.itis.forms;

import lombok.Data;

@Data
public class ItemForm {
    private String name;
    private int price;
    private String link;
    private Long wishListId;
}
