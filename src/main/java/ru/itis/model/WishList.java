package ru.itis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @JsonIgnore
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "author")
    private User author;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL)
    private List<Item> items;

    public static WishList getDefault() {
        return WishList.builder()
                .id(null)
                .title("Null wish list")
                .author(User.getDefaultUser())
                .items(null)
                .build();
    }

    public WishList(String title, User author) {
        this.title = title;
        this.author = author;
    }
}
