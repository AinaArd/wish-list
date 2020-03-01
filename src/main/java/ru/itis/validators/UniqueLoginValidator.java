package ru.itis.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.repositories.UsersRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private UsersRepository usersRepository;

    @Autowired
    public UniqueLoginValidator(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !usersRepository.findByLogin(value).isPresent();
    }
}
