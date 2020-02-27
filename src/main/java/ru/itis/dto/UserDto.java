package ru.itis.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.validators.UniqueLogin;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {

    @NotBlank
    @UniqueLogin
    private String login;

    @NotBlank
    private String password;
}
