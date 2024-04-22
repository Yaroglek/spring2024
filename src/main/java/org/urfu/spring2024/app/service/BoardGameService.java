package org.urfu.spring2024.app.service;

import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.urfu.spring2024.app.repository.BoardGameRepository;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Category;
import org.urfu.spring2024.domain.QBoardGame;

import java.util.List;

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
            log.info("Создана игра с ID {}", game.getId());
            return game;
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при сохранении новой игры", e);
        }
    }

    /**
     * Поиск игры в БД по её id.
     *
     * @param gameId - уникальный идентификатор для поиска игры.
     * @return - игра с указанным id.
     */
    @NonNull
    public BoardGame getBoardGameById(long gameId) {
        var searchedGame = boardGameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Игра с ID " + gameId + " не найдена"));
        log.debug("Игра с ID {} найдена", gameId);
        return searchedGame;
    }

    /**
     * Удаление игры из БД по её id.
     *
     * @param gameId - уникальный идентификатор для поиска игры.
     */
    public void deleteBoardGameById(long gameId) {
        boardGameRepository.deleteById(gameId);
        log.info("Игра с ID {} удалена", gameId);
    }

    /**
     * Добавление категории в список категорий игры.
     *
     * @param gameId     - уникальный идентификатор для поиска игры.
     * @param categoryId - уникальный идентификатор для поиска категории.
     */
    public void addCategory(long gameId, long categoryId) {
        BoardGame game = getBoardGameById(gameId);
        Category category = categoryService.getCategoryById(categoryId);

        if (!game.getCategories().contains(category)) {
            game.getCategories().add(category);
            boardGameRepository.save(game);
            log.info("Игра {} (ID {}) принадлежит к категории {} (ID {})", game.getName(), gameId, category.getName(), categoryId);
        }
    }

    /**
     * Удаление категории из списка категорий игры.
     *
     * @param gameId     - уникальный идентификатор для поиска игры.
     * @param categoryId - уникальный идентификатор для поиска категории.
     */
    public void removeCategory(long gameId, long categoryId) {
        BoardGame game = getBoardGameById(gameId);
        Category category = categoryService.getCategoryById(categoryId);

        if (game.getCategories().contains(category)) {
            game.getCategories().remove(category);
            boardGameRepository.save(game);
            log.info("Игра {} (ID {}) более не принадлежит к категории {} (ID {})", game.getName(), gameId, category.getName(), categoryId);
        }
    }

    /**
     * Полчуение всех игр из базы данных.
     *
     * @return - список всех игр.
     */
    public List<BoardGame> getAllBoardGames() {
        return boardGameRepository.findAll();
    }

    /**
     * Полчуение всех игр по определенным фильтрам.
     *
     * @param name            - фильтрация по имени.
     * @param categoryIds     - фильтрация по соответствии категориям.
     * @param recommendedAge  - фильтрация по возрасту.
     * @param amountOfPlayers - фильтрация по количеству игроков.
     * @return - список игр.
     */
    public List<BoardGame> getBoardGamesWithFilters(String name, List<Long> categoryIds, int recommendedAge, int amountOfPlayers) {
        QBoardGame boardGame = QBoardGame.boardGame;
        BooleanBuilder predicate = new BooleanBuilder();

        if (name != null && !name.isEmpty()) {
            predicate.and(boardGame.name.containsIgnoreCase(name));
        }

        if (categoryIds != null && !categoryIds.isEmpty()) {
            predicate.and(boardGame.categories.any().id.in(categoryIds));
        }

        if (recommendedAge != 0) {
            predicate.and(boardGame.recommendedAge.loe(recommendedAge));
        }

        if (amountOfPlayers != 0) {
            predicate.and(boardGame.amountOfPlayers.eq(amountOfPlayers));
        }

        return boardGameRepository.findAll(predicate);
    }
}
