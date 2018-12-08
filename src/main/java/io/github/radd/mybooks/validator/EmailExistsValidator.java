package io.github.radd.mybooks.validator;

import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.dto.UserSignUpDTO;
import io.github.radd.mybooks.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistsValidator implements ConstraintValidator<EmailExists, String> {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        User user = repository.findByEmail(value);
        return (user == null) ? true : false;
    }

}
