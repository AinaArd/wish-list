package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private String link;
    private String description;

    @ManyToOne
    @JoinColumn(name = "wishList")
    private WishList wishList;

    public Item(String name, int price, String link, String description, WishList wishList) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.description = description;
        this.wishList = wishList;
    }
}
