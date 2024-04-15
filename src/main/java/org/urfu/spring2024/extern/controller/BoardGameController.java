package org.urfu.spring2024.extern.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urfu.spring2024.app.service.BoardGameService;
import org.urfu.spring2024.app.service.ManufacturerService;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Manufacturer;
import org.urfu.spring2024.extern.assembler.BoardGameAssembler;
import org.urfu.spring2024.extern.dto.BoardGameDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
public class BoardGameController {
    private BoardGameService boardGameService;
    private BoardGameAssembler boardGameAssembler;
    private ManufacturerService manufacturerService;

    @PostMapping
    public ResponseEntity<BoardGameDTO> createGame(@RequestBody BoardGameDTO gameDTO) {

        BoardGame newGame = BoardGame.builder()
                .name(gameDTO.getName())
                .releaseDate(gameDTO.getReleaseDate())
                .manufacturer(manufacturerService.getManufacturerById(gameDTO.getManufacturerId()))
                .description(gameDTO.getDescription())
                .recommendedAge(gameDTO.getRecommendedAge())
                .amountOfPlayers(gameDTO.getAmountOfPlayers())
                .categories(new ArrayList<>())
                .reviews(new ArrayList<>())
                .events(new ArrayList<>())
                .discussions(new ArrayList<>())
                .build();

        boardGameService.createBoardGame(newGame);

        return new ResponseEntity<>(boardGameAssembler.toModel(newGame), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BoardGameDTO>> getAllBoardGames() {
        List<BoardGame> allGames = boardGameService.getAllBoardGames();

        List<BoardGameDTO> gameDTOS = new ArrayList<>();
        for (BoardGame game : allGames) {
            BoardGameDTO gameDTO = boardGameAssembler.toModel(game);
            gameDTOS.add(gameDTO);
        }

        return ResponseEntity.ok(gameDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGameDTO> getBoardGameByID(@PathVariable long id) {
        BoardGame game = boardGameService.getBoardGameById(id);
        return ResponseEntity.ok(boardGameAssembler.toModel(game));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardGameByID(@PathVariable long id) {
        boardGameService.deleteBoardGameById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{gameId}/trackedGames/{categoryId}")
    public ResponseEntity<Void> addCategory(@PathVariable long gameId, @PathVariable long categoryId) {
        boardGameService.addCategory(gameId, categoryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{gameId}/trackedGames/{categoryId}")
    public ResponseEntity<Void> removeCategory(@PathVariable long gameId, @PathVariable long categoryId) {
        boardGameService.removeCategory(gameId, categoryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BoardGameDTO>> getBoardGamesByName(@RequestParam String name) {
        List<BoardGame> searchedGames = boardGameService.getBoardGamesByName(name);

        List<BoardGameDTO> gameDTOS = new ArrayList<>();
        for (BoardGame game : searchedGames) {
            BoardGameDTO gameDTO = boardGameAssembler.toModel(game);
            gameDTOS.add(gameDTO);
        }

        return ResponseEntity.ok(gameDTOS);
    }
}
