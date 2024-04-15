package org.urfu.spring2024.extern.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.urfu.spring2024.domain.*;
import org.urfu.spring2024.extern.controller.BoardGameController;
import org.urfu.spring2024.extern.dto.BoardGameDTO;

import java.util.stream.Collectors;

@Component
public class BoardGameAssembler extends RepresentationModelAssemblerSupport<BoardGame, BoardGameDTO> {
    public BoardGameAssembler() {
        super(BoardGameController.class, BoardGameDTO.class);
    }

    @Override
    public BoardGameDTO toModel(BoardGame game) {
        BoardGameDTO gameDTO = instantiateModel(game);

        gameDTO.setId(game.getId());
        gameDTO.setName(game.getName());
        gameDTO.setDescription(game.getDescription());
        gameDTO.setReleaseDate(game.getReleaseDate());
        gameDTO.setManufacturerId(game.getManufacturer().getId());
        gameDTO.setRecommendedAge(game.getRecommendedAge());
        gameDTO.setAmountOfPlayers(game.getAmountOfPlayers());
        gameDTO.setCategoriesIds(game.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList()));
        gameDTO.setReviewsIds(game.getReviews().stream()
                .map(Review::getId)
                .collect(Collectors.toList()));
        gameDTO.setEventsIds(game.getEvents().stream()
                .map(Event::getId)
                .collect(Collectors.toList()));
        gameDTO.setDiscussionsIds(game.getDiscussions().stream()
                .map(Discussion::getId)
                .collect(Collectors.toList()));

        return gameDTO;
    }
}
