package io.github.radd.mybooks;

import io.github.radd.mybooks.domain.Role;
import io.github.radd.mybooks.domain.User;
import io.github.radd.mybooks.utils.user.UserInfo;
import io.github.radd.mybooks.utils.user.UserRole;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class UniTests {

    @Test
    public void checkUserRoleEnum() {
        assertEquals(UserRole.ADMIN.getName(), "ROLE_ADMIN");
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


}
