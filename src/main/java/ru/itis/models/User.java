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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<WishList> wishLists;

    public static User getDefaultUser() {
        return User.builder()
                .id(null)
                .login("Null login")
                .wishLists(null)
                .build();
    }
}
