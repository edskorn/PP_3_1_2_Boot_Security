package ru.kata.spring.boot_security.demo.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqLoginValidator implements ConstraintValidator<UniqLogin, User> {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqLogin constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        boolean result=true;
        try {
            Optional<User> userFromDB = userService.getUserByUsername(user.getUsername());
            if (userFromDB.isEmpty()) {
                result = true;
            } else if (user.equals(userFromDB.get())) {
                result = true;
            } else {
                result = false;
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate("Пользователь с таким логином уже зарегистрирован в системе")
                        .addPropertyNode("username")
                        .addConstraintViolation();
            }
        } catch (Exception e)
        {
            //
        }
        return result;
    }

}
