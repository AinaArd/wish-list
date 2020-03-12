package ru.itis.validator;

import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private UserRepository userRepository;

    @Autowired
    public UniqueLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !userRepository.findByLogin(value).isPresent();
    }
}
