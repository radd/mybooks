package io.github.radd.mybooks.utils.auth;

import io.github.radd.mybooks.utils.user.UserInfo;
import org.springframework.security.core.Authentication;

public interface AuthUser {
    Authentication getAuthentication();
    boolean isLoggedIn();
    UserInfo getUserInfo();
    boolean isAnonymous();
}
