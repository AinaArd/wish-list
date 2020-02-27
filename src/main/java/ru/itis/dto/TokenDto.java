package ru.itis.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.models.Token;

@Data
@Builder
public class TokenDto {
    private String value;

    public static TokenDto from(Token token) {
        return TokenDto.builder()
                .value(token.getValue())
                .build();
    }
}
