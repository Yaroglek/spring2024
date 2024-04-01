package org.urfu.spring2024.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.urfu.spring2024.app.service.UserService;
import org.urfu.spring2024.domain.User;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Sql("/test.sql")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterNewUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("1234");
        user.setEmail("test@example.com");
        User registeredUser = userService.registerNewUser(user);

        assertNotNull(registeredUser.getId());
    }

    @Test
    public void testUserNotRegistered() {
        assertThrows(IllegalArgumentException.class, () -> userService.registerNewUser(null));
    }

    @Test
    public void testFindUserByID() {
        User user = userService.getUserById(1);
        assertNotNull(user);
        assertEquals(1, user.getId());
    }

    @Test
    public void testUserNotFoundByID() {
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(3));
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUserById(2);
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(2));
    }

    @Test
    public void testTrackGame() {
        userService.trackGame(1, 1);
        User user = userService.getUserById(1);

        assertEquals(1, user.getTrackedGames().size());
    }
}
