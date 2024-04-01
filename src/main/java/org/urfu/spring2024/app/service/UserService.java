package org.urfu.spring2024.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.urfu.spring2024.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.User;

/**
 * Сервисный класс для работы с пользователем.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BoardGameService boardGameService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Регистрация нового пользователя. Происходит шифрование пароля с помощью BCrypt
     *
     * @param user - объект пользователя для регистрации.
     * @return - зарегестрированный пользователь.
     */
    public User registerNewUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null");
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            log.info("Создан пользователь с id {}", savedUser.getId());
            return savedUser;
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при регистрации нового пользователя", e);
        }
    }

    /**
     * Поиск пользователя в БД по его id.
     *
     * @param userID - уникальный идентификатор для поиска пользователя.
     * @return - пользователь с указанным id.
     */
    public User getUserById(long userID) {
        User searchedUser = userRepository.findById(userID);
        if (searchedUser == null) {
            throw new IllegalArgumentException("Пользователь с ID " + userID + " не найден");
        } else {
            log.info("Пользователь с ID {} найден", userID);
            return searchedUser;
        }
    }

    /**
     * Удаление пользователя из БД по его id.
     *
     * @param userID - уникальный идентификатор для поиска пользователя.
     */
    public void deleteUserById(long userID) {
        User searchedUser = userRepository.findById(userID);
        if (searchedUser == null) {
            throw new IllegalArgumentException("Пользователь с ID " + userID + " не найден");
        } else {
            userRepository.deleteById(userID);
            log.info("Пользователь с ID {} удален", userID);
        }
    }

    /**
     * Добавление игры в список отслеживаемых пользователем игр.
     *
     * @param userID - уникальный идентификатор для поиска пользователя.
     * @param gameID - уникальный идентификатор для поиска игры.
     */
    public void trackGame(long userID, long gameID) {
        User user = getUserById(userID);
        BoardGame game = boardGameService.getBoardGameByID(gameID);

        if (!user.getTrackedGames().contains(game)) {
            user.getTrackedGames().add(game);
            userRepository.save(user);
            log.info("Пользователь с ID {} отслеживает игру {} (ID {})", userID, game.getName(), gameID);
        }
    }

    //TODO Методы для смены данных пользователя
}
