package org.urfu.spring2024.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.urfu.spring2024.app.repository.BoardGameRepository;
import org.urfu.spring2024.app.service.BoardGameService;
import org.urfu.spring2024.domain.BoardGame;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class BoardGameServiceTest {
    @Mock
    private BoardGameRepository boardGameRepository;

    @InjectMocks
    private BoardGameService boardGameService;

    @Test
    public void testCreateBoardGame() {
        BoardGame game = BoardGame.builder().id(1L).name("game1").build();

        when(boardGameRepository.save(game)).thenReturn(game);
        BoardGame registeredUser = boardGameService.createBoardGame(game);

        assertNotNull(registeredUser.getId());
    }

    @Test
    public void testBoardGameNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> boardGameService.createBoardGame(null));
    }

    @Test
    public void testFindBoardGameByID() {
        BoardGame game = BoardGame.builder().id(1L).name("game1").build();

        when(boardGameRepository.findById(1L)).thenReturn(game);
        BoardGame searchedGame = boardGameService.getBoardGameByID(1L);

        assertNotNull(searchedGame);
        assertEquals(1L, searchedGame.getId());
    }

    @Test
    public void testBoardGameNotFoundByID() {
        when(boardGameRepository.findById(3L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> boardGameService.getBoardGameByID(3L));
    }

    @Test
    public void testDeleteBoardGame() {
        BoardGame game = BoardGame.builder().id(4L).name("game4").build();

        when(boardGameRepository.findById(4L)).thenReturn(game);
        boardGameService.deleteBoardGameByID(4L);

        verify(boardGameRepository, times(1)).deleteById(4L);
    }
}
