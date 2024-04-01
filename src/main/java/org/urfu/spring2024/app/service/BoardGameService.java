package org.urfu.spring2024.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.urfu.spring2024.app.repository.BoardGameRepository;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Category;

/**
 * Сервисный класс для работы с польователем.
 */
@Slf4j
@Service
@AllArgsConstructor
public class BoardGameService {
    private final BoardGameRepository boardGameRepository;
    private final CategoryService categoryService;

    /**
     * Сохранение в БД новой настольной игры.
     *
     * @param game - объект игры для сохранения в БД.
     * @return - сохраненная игра.
     */
    public BoardGame createBoardGame(BoardGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Игра не может быть null");
        }
        try {
            boardGameRepository.save(game);
            log.info("Создан пользователь с ID {}", game.getId());
            return game;
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при сохранении новой игры", e);
        }
    }

    /**
     * Поиск игры в БД по её id.
     *
     * @param gameID - уникальный идентификатор для поиска игры.
     * @return - игра с указанным id.
     */
    public BoardGame getBoardGameByID(long gameID) {
        BoardGame searchedGame = boardGameRepository.findById(gameID);
        if (searchedGame == null) {
            throw new IllegalArgumentException("Игра с ID " + gameID + " не найдена");
        } else {
            log.info("Игра с ID {} найдена", gameID);
            return searchedGame;
        }
    }

    /**
     * Удаление игры из БД по её id.
     *
     * @param gameID - уникальный идентификатор для поиска игры.
     */
    public void deleteBoardGameByID(long gameID) {
        BoardGame searchedGame = boardGameRepository.findById(gameID);
        if (searchedGame == null) {
            throw new IllegalArgumentException("Игра с ID " + gameID + " не найдена");
        } else {
            boardGameRepository.deleteById(gameID);
            log.info("Игра с ID {} удалена", gameID);
        }
    }

    /**
     * Добавление категории в список категорий игры.
     *
     * @param gameID - уникальный идентификатор для поиска игры.
     * @param categoryID - уникальный идентификатор для поиска категории.
     */
    public void addCategory(long gameID, long categoryID) {
        BoardGame game = getBoardGameByID(gameID);
        Category category = categoryService.getCategoryByID(categoryID);

        if (!game.getCategories().contains(category)) {
            game.getCategories().add(category);
            boardGameRepository.save(game);
            log.info("Игра {} (ID {}) принадлежит к категории {} (ID {})", game.getName(), gameID, category.getName(), categoryID);
        }
    }

    //TODO остальные методы сервиса
}
