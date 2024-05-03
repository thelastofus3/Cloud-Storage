package com.thelastofus.cloudstorage.util;

import com.thelastofus.cloudstorage.dto.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        UserRegistrationDto user = (UserRegistrationDto) o;
        boolean valid = user.getPassword() != null && user.getMatchingPassword() != null &&
                user.getPassword().equals(user.getMatchingPassword());
        if (!valid) {
            constraintValidatorContext.
                    buildConstraintViolationWithTemplate(constraintValidatorContext
                            .getDefaultConstraintMessageTemplate())
                    .addPropertyNode("matchingPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
