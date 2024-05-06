package org.urfu.spring2024.app.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.urfu.spring2024.app.repository.UserRepository;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BoardGameService boardGameService;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterNewUser() {
        User user = User.builder().id(1L).username("user1").build();

        when(userRepository.save(user)).thenReturn(user);
        User registeredUser = userService.createUser(user);

        assertNotNull(registeredUser.getId());
    }

    @Test
    public void testUserNotRegistered() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
    }

    @Test
    public void testFindUserByID() {
        User user = User.builder().id(1L).username("user1").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User searchedUser = userService.getUserById(1L);

        assertNotNull(searchedUser);
        assertEquals(1L, searchedUser.getId());
    }

    @Test
    public void testUserNotFoundByID() {
        when(userRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(3L));
    }

    @Test
    public void testDeleteUser() {
        User user = User.builder().id(3L).username("user1").build();

        when(userRepository.findById(3L)).thenReturn(Optional.of(user));
        userService.deleteUserById(3L);

        verify(userRepository, times(1)).deleteById(3L);
    }

    @Test
    public void testTrackGame() {
        User user = User.builder().id(1L).username("user1").trackedGames(new ArrayList<>()).build();
        BoardGame game = BoardGame.builder().id(1L).name("game1").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(boardGameService.getBoardGameById(1L)).thenReturn(game);

        userService.trackGame(1L, 1L);

        assertEquals(1, user.getTrackedGames().size());
    }

    @Test
    public void testUnTrackGame() {
        User user = User.builder().id(1L).username("user1").trackedGames(new ArrayList<>()).build();
        BoardGame game = BoardGame.builder().id(1L).name("game1").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(boardGameService.getBoardGameById(1L)).thenReturn(game);

        userService.trackGame(1L, 1L);
        assertEquals(1, user.getTrackedGames().size());

        userService.unTrackGame(1L, 1L);
        assertEquals(0, user.getTrackedGames().size());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }
}
