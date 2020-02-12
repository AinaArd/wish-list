package ru.itis.models;

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
    @JoinColumn(name = "author")
    private User author;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "wishList")
    private List<Item> items;

}