package io.github.radd.mybooks;

import io.github.radd.mybooks.domain.Role;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.utils.user.UserInfo;
import io.github.radd.mybooks.utils.user.UserRole;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class UnitTests {

    @Test
    public void checkUserRoleEnum() {
        assertEquals(UserRole.ADMIN.getName(), "ROLE_ADMIN");
        assertEquals(UserRole.MODERATOR.getName(), "ROLE_MODERATOR");
        assertEquals(UserRole.USER.getName(), "ROLE_USER");

        assertTrue(UserRole.ADMIN.is("ROLE_ADMIN"));
        assertTrue(UserRole.MODERATOR.is("ROLE_MODERATOR"));
        assertTrue(UserRole.USER.is("ROLE_USER"));

    }

    @Test
    public void checkIfUserHasRole() {
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");

        Role adminRole = new Role();
        adminRole.setName(UserRole.ADMIN.getName());

        user.setRoles(Arrays.asList(adminRole));

        UserInfo userInfo = new UserInfo(user, UserInfo.UserGrantedAuthorities.getAuthorities(user.getRoles()));

        assertTrue(userInfo.isAdmin());
        assertFalse(userInfo.isModerator());

    }

    @Test
    public void check_getAllUserRoles_String() {
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");

        Role adminRole = new Role();
        adminRole.setName(UserRole.ADMIN.getName());
        Role moderatorRole = new Role();
        moderatorRole.setName(UserRole.MODERATOR.getName());
        Role userRole = new Role();
        userRole.setName(UserRole.USER.getName());

        user.setRoles(Arrays.asList(adminRole, moderatorRole, userRole));

        UserInfo userInfo = new UserInfo(user, UserInfo.UserGrantedAuthorities.getAuthorities(user.getRoles()));

        assertEquals(userInfo.getAllUserRoles(), UserRole.ADMIN.getName() + " " + UserRole.MODERATOR.getName() + " " + UserRole.USER.getName());

    }


}
