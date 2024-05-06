package org.urfu.spring2024.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.urfu.spring2024.app.repository.UserRepository;

import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.User;

import java.util.List;

/**
 * Сервисный класс для работы с пользователем.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BoardGameService boardGameService;

    /**
     * Регистрация нового пользователя. Происходит шифрование пароля с помощью BCrypt
     *
     * @param user - объект пользователя для регистрации.
     * @return - зарегестрированный пользователь.
     */
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null");
        }
        try {
            userRepository.save(user);
            log.info("Создан пользователь с ID {}", user.getId());
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при сохранении нового пользователя", e);
        }
    }

    /**
     * Поиск пользователя в БД по его id.
     *
     * @param userId - уникальный идентификатор для поиска пользователя.
     * @return - пользователь с указанным id.
     */
    public User getUserById(long userId) {
        var searchedUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + userId + " не найден"));
        log.debug("Пользователь с ID {} найден", userId);
        return searchedUser;
    }

    /**
     * Удаление пользователя из БД по его id.
     *
     * @param userId - уникальный идентификатор для поиска пользователя.
     */
    public void deleteUserById(long userId) {
        userRepository.deleteById(userId);
        log.info("Пользователь с ID {} удален", userId);
    }

    /**
     * Добавление игры в список отслеживаемых пользователем игр.
     *
     * @param userId - уникальный идентификатор для поиска пользователя.
     * @param gameId - уникальный идентификатор для поиска игры.
     */
    public void trackGame(long userId, long gameId) {
        User user = getUserById(userId);
        BoardGame game = boardGameService.getBoardGameById(gameId);

        if (!user.getTrackedGames().contains(game)) {
            user.getTrackedGames().add(game);
            userRepository.save(user);
            log.info("Пользователь с ID {} отслеживает игру {} (ID {})", userId, game.getName(), gameId);
        }
    }

    /**
     * Удаление игры из списка отслеживаемых пользователем игр.
     *
     * @param userId - уникальный идентификатор для поиска пользователя.
     * @param gameId - уникальный идентификатор для поиска игры.
     */
    public void unTrackGame(long userId, long gameId) {
        User user = getUserById(userId);
        BoardGame game = boardGameService.getBoardGameById(gameId);

        if (user.getTrackedGames().contains(game)) {
            user.getTrackedGames().remove(game);
            userRepository.save(user);
            log.info("Пользователь с ID {} более не отслеживает игру {} (ID {})", userId, game.getName(), gameId);
        }
    }

    /**
     * Полчуение всех пользователей из базы данных.
     *
     * @return - список всех пользователей.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
