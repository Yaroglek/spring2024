package org.urfu.spring2024;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.urfu.spring2024.app.UserService;
import org.urfu.spring2024.domain.User;
import org.urfu.spring2024.extern.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
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

    @Sql("/test.sql")
    @Test
    public void testFindUserByID() {
        User searchedUser = userService.getUserById(1);
        assertNotNull(searchedUser);
        assertEquals(1, searchedUser.getId());
    }

    @Sql("/test.sql")
    @Test
    public void testUserNotFoundByID() {
        assertNull(userService.getUserById(3));
    }
}
