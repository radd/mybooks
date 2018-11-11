package io.github.radd.mybooks.domain.dto;

import io.github.radd.mybooks.validator.PasswordMatches;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
public class UserSignUpDTO {

    @NotNull
    @NotEmpty(message = "{UserSignUp.name.NotEmpty}")
    private String name;

    @NotNull
    @NotEmpty(message = "{UserSignUp.lastName.NotEmpty}")
    private String lastName;

    @Email(message = "{UserSignUp.email.Email}")
    @NotNull
    @NotEmpty(message = "{UserSignUp.email.NotEmpty}")
    private String email;

    @NotNull
    @NotEmpty(message = "{UserSignUp.password.NotEmpty}")
    private String password;

    @NotNull
    @NotEmpty(message = "{UserSignUp.matchingPassword.NotEmpty}")
    private String matchingPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

}