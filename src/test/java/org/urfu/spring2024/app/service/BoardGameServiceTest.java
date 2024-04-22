package org.urfu.spring2024.app.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.urfu.spring2024.app.repository.BoardGameRepository;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class BoardGameServiceTest {
    @Mock
    private CategoryService categoryService;
    @Mock
    private BoardGameRepository boardGameRepository;

    @InjectMocks
    private BoardGameService boardGameService;

    @Test
    public void testCreateBoardGame() {
        BoardGame game = BoardGame.builder().id(1L).name("game1").build();

        when(boardGameRepository.save(game)).thenReturn(game);
        BoardGame createdGame = boardGameService.createBoardGame(game);

        assertNotNull(createdGame.getId());
    }

    @Test
    public void testBoardGameNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> boardGameService.createBoardGame(null));
    }

    @Test
    public void testFindBoardGameByID() {
        BoardGame game = BoardGame.builder().id(1L).name("game1").build();

        when(boardGameRepository.findById(1L)).thenReturn(Optional.of(game));
        BoardGame searchedGame = boardGameService.getBoardGameById(1L);

        assertNotNull(searchedGame);
        assertEquals(1L, searchedGame.getId());
    }

    @Test
    public void testBoardGameNotFoundByID() {
        when(boardGameRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> boardGameService.getBoardGameById(3L));
    }

    @Test
    public void testDeleteBoardGame() {
        BoardGame game = BoardGame.builder().id(4L).name("game4").build();

        when(boardGameRepository.findById(4L)).thenReturn(Optional.of(game));
        boardGameService.deleteBoardGameById(4L);

        verify(boardGameRepository, times(1)).deleteById(4L);
    }

    @Test
    public void testAddCategory() {
        BoardGame game = BoardGame.builder().id(1L).name("game1").categories(new ArrayList<>()).build();
        Category category = Category.builder().id(1L).name("category1").build();

        when(boardGameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(categoryService.getCategoryById(1L)).thenReturn(category);

        boardGameService.addCategory(1L, 1L);

        assertEquals(1, game.getCategories().size());
    }

    @Test
    public void testRemoveCategory() {
        BoardGame game = BoardGame.builder().id(1L).name("game1").categories(new ArrayList<>()).build();
        Category category = Category.builder().id(1L).name("category1").build();

        when(boardGameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(categoryService.getCategoryById(1L)).thenReturn(category);

        boardGameService.addCategory(1L, 1L);
        assertEquals(1, game.getCategories().size());

        boardGameService.removeCategory(1L, 1L);
        assertEquals(0, game.getCategories().size());
    }

    @Test
    public void testGetAllBoardGames() {
        List<BoardGame> games = new ArrayList<>();
        games.add(new BoardGame());
        games.add(new BoardGame());

        when(boardGameRepository.findAll()).thenReturn(games);

        List<BoardGame> result = boardGameService.getAllBoardGames();
        assertEquals(2, result.size());
    }
}
