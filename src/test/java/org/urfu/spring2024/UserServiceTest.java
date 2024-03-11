package org.urfu.spring2024;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.urfu.spring2024.app.UserService;
import org.urfu.spring2024.domain.User;
import org.urfu.spring2024.extern.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

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
    public void testFindUserByID() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("1234");
        user.setEmail("test@example.com");
        User registeredUser = userService.registerNewUser(user);

        long registeredUserId = registeredUser.getId();
        User searchedUser = userService.getUserById(registeredUserId);
        assertNotNull(searchedUser);
        assertEquals(registeredUserId, searchedUser.getId());
    }
}
