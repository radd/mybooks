package io.github.radd.mybooks.configuration;


import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.domain.repository.UserRepository;
import io.github.radd.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Component
public class AutoLogin {

    boolean alreadySetup = false;


    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void signUp(HttpServletRequest request) {
        if (alreadySetup)
            return;
        authenticateUserAndSetSession("test@test.com", request);

    }


    private void authenticateUserAndSetSession(String username, HttpServletRequest request){

        final User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        UserDetails userDetails = new UserInfo(user, user.getEmail(), user.getPassword(), UserInfo.UserGrantedAuthorities.getAuthorities(user.getRoles()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        alreadySetup = true;

    }

    public boolean isAlreadySetup() {
        return alreadySetup;
    }

    public void setAlreadySetup(boolean alreadySetup) {
        this.alreadySetup = alreadySetup;
    }
}
