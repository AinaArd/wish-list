package ru.itis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

   private UserService userService;

   @Autowired
   public UniqueLoginValidator(UserService userService) {
      this.userService = userService;
   }

   public boolean isValid(String value, ConstraintValidatorContext context) {
      return value != null && !userService.checkIfExists(value);   }
}
