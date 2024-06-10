package com.thelastofus.cloudstorage.util.password;

import com.thelastofus.cloudstorage.dto.user.UserRegistration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        UserRegistration user = (UserRegistration) o;
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
