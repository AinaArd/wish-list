package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "user")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime expiredDateTime;

    public Token(String value, User user, LocalDateTime createdAt, LocalDateTime expiredDateTime) {
        this.value = value;
        this.user = user;
        this.createdAt = createdAt;
        this.expiredDateTime = expiredDateTime;
    }
}
