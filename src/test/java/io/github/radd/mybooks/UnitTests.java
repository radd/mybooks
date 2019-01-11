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
    public void checkIfUserHasRole() {
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");

        Role adminRole = new Role();
        adminRole.setName(UserRole.ADMIN);

        user.setRoles(Arrays.asList(adminRole));

        UserInfo userInfo = new UserInfo(user, user.getEmail(), user.getPassword(),
                UserInfo.UserGrantedAuthorities.getAuthorities(user.getRoles()));

        assertTrue(userInfo.isAdmin());
        assertFalse(userInfo.isModerator());

    }

    @Test
    public void check_getAllUserRoles_String() {
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");

        Role adminRole = new Role();
        adminRole.setName(UserRole.ADMIN);
        Role moderatorRole = new Role();
        moderatorRole.setName(UserRole.MODERATOR);
        Role userRole = new Role();
        userRole.setName(UserRole.USER);

        user.setRoles(Arrays.asList(adminRole, moderatorRole, userRole));

        UserInfo userInfo = new UserInfo(user, user.getEmail(), user.getPassword(),
                UserInfo.UserGrantedAuthorities.getAuthorities(user.getRoles()));

        assertEquals(userInfo.getAllUserRoles(), UserRole.ADMIN + " " + UserRole.MODERATOR + " " + UserRole.USER);

    }

    @Test(expected = IllegalArgumentException.class)
    public void userInfo_constructor_IllegalArgumentException() {
        UserInfo userInfo = new UserInfo(null, "", "", Arrays.asList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void userInfo_setUser_IllegalArgumentException() {
        UserInfo userInfo = new UserInfo(new User(), "", "", Arrays.asList());
        userInfo.setUser(null);
    }

/*    @Test
    public void aa() {
        BookService bookService = new BookService();
        List<Long> ids = bookService.getIDsFromString(",1dd,4,6da,,,dnjdbjd,8,");
        assertArrayEquals(new Long[]{4,8}, ids.toArray());

    }*/


}
