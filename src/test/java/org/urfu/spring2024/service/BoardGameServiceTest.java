package org.urfu.spring2024.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.urfu.spring2024.app.service.BoardGameService;
import org.urfu.spring2024.domain.BoardGame;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql("/test.sql")
class BoardGameServiceTest {

    @Autowired
    private BoardGameService boardGameService;

    @Test
    public void testCreateBoardGame() {
        BoardGame game = new BoardGame();
        BoardGame createdGame = boardGameService.createBoardGame(game);

        assertNotNull(createdGame.getId());
    }

    @Test
    public void testFindBoardGameByID() {
        BoardGame searchedGame = boardGameService.getBoardGameByID(1);

        assertNotNull(searchedGame);
        assertEquals(1, searchedGame.getId());
    }

    @Test
    public void testBoardGameNotFoundByID() {
        assertThrows(IllegalArgumentException.class, () -> boardGameService.getBoardGameByID(3));
    }

    @Test
    public void testDeleteBoardGame() {
        boardGameService.deleteBoardGameByID(2);
        assertThrows(IllegalArgumentException.class, () -> boardGameService.getBoardGameByID(2));
    }

    @Test
    public void testAddCategory() {
        //TODO
    }
}
