package io.github.radd.mybooks.utils.user;

import io.github.radd.mybooks.domain.Role;
import io.github.radd.mybooks.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserInfo extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserInfo(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
        this.user = user;
        init();
    }

    //For GrantedAuthorities - hasRole(), hasAuthority() - spring method
    public static class UserGrantedAuthorities {
        public static Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            for (final Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return authorities;
        }
    }

    private void init() {
    }


    public boolean isAdmin() {
        return user.getRoles().stream().anyMatch(r -> r.getName().equals(UserRole.ADMIN.getName()));
    }

    public boolean isModerator() {
        return user.getRoles().stream().anyMatch(r -> r.getName().equals(UserRole.MODERATOR.getName()));
    }

    public String getAllUserRoles() {
        String output = "";

        for(Role role : user.getRoles())
            output += role.getName() + " ";

        return output.trim();
    }


    public User getUser() {
        return user;
    }
}
