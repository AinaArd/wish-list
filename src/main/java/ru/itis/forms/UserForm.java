package ru.itis.forms;

import lombok.Data;
import ru.itis.utils.UniqueLogin;

import javax.validation.constraints.NotBlank;

@Data
public class UserForm {

    @NotBlank
    @UniqueLogin
    public String login;

    @NotBlank
    public String password;
}
