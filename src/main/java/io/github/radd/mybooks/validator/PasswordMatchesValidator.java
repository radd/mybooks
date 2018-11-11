package io.github.radd.mybooks.validator;

import io.github.radd.mybooks.domain.dto.UserSignUpDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserSignUpDTO user = (UserSignUpDTO) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
