package io.github.radd.mybooks.utils.auth;

import org.springframework.security.core.Authentication;

public interface AuthUser {
    Authentication getAuthentication();
}
