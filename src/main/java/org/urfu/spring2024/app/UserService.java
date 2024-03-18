package org.urfu.spring2024.app;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urfu.spring2024.domain.User;
import org.urfu.spring2024.extern.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Сервисный класс для работы с польователем.
 */
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрация нового пользователя. Происходит преобразование пароля в более сложный с помощью BCrypt
     *
     * @param user - объект пользователя для регистрации.
     * @return - зарегестрированный пользователь.
     */
    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("Создан пользователь с ID {}", user.getId());
        return user;
    }

    /**
     * Поиск пользователя в БД по его id.
     *
     * @param userId - уникальный идентификатор для поиска пользователя.
     * @return - пользователь с указанным id.
     */
    public User getUserById(long userId) {
        User searchedUser = userRepository.findById(userId);
        if (searchedUser == null) {
            log.error("Пользователь с ID {} не найден", userId);
            return null;
        } else {
            log.info("Пользователь с ID {} найден", userId);
            return searchedUser;
        }
    }

    //TODO доделать остальные методы
}
