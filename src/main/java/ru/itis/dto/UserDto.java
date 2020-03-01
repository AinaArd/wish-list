package ru.itis.dto;

import lombok.Data;
import ru.itis.validators.UniqueLogin;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    @NotBlank
    @UniqueLogin
    private String login;

    @NotBlank
    private String password;
}
