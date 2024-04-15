package org.urfu.spring2024.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.urfu.spring2024.app.repository.BoardGameRepository;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Category;

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
    public BoardGame getBoardGameById(long gameId) {
        var searchedGame = boardGameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Игра с ID " + gameId + " не найдена"));
        log.info("Игра с ID {} найдена", gameId);
        return searchedGame;
    }

    /**
     * Удаление игры из БД по её id.
     *
     * @param gameId - уникальный идентификатор для поиска игры.
     */
    public void deleteBoardGameById(long gameId) {
        BoardGame searchedGame = getBoardGameById(gameId);
        if (searchedGame == null) {
            throw new IllegalArgumentException("Игра с ID " + gameId + " не найдена");
        } else {
            boardGameRepository.deleteById(gameId);
            log.info("Игра с ID {} удалена", gameId);
        }
    }

    /**
     * Добавление категории в список категорий игры.
     *
     * @param gameId - уникальный идентификатор для поиска игры.
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
     * @param gameId - уникальный идентификатор для поиска игры.
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
     * Полчуение всех игр, содержащих в названии определенную строку.
     *
     * @param name - строка для поиска
     * @return - список игр.
     */
    public List<BoardGame> getBoardGamesByName(String name) {
        return boardGameRepository.findAllByNameContainsIgnoreCase(name);
    }

//    /**
//     * Полчуение всех игр, подпадающих под определенную категорию.
//     *
//     * @param category - категория для поиска.
//     * @return - список игр.
//     */
//    public List<BoardGame> getBoardGamesByCategory(Category category) {
//        return boardGameRepository.findBoardGamesByCategoriesContains(category);
//    }
//
//    /**
//     * Полчуение всех игр, для которых рекомендованный возраст меньше запрашиваемого.
//     *
//     * @param age - запрашиваемы возраст.
//     * @return - список игр.
//     */
//    public List<BoardGame> getBoardGamesByRecommendedAge(int age) {
//        return boardGameRepository.findBoardGamesByRecommendedAgeLessThanEqual(age);
//    }
//
//    /**
//     * Полчуение всех игр, для которых рекомендованное количество игроков равняется запрашиваемому.
//     *
//     * @param players - запрашиваемое количество игроков.
//     * @return - список игр.
//     */
//    public List<BoardGame> getBoardGamesByAmountOfPlayers(int players) {
//        return boardGameRepository.findBoardGamesByAmountOfPlayersEquals(players);
//    }
}
