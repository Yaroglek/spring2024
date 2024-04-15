package org.urfu.spring2024.extern.assembler;


import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Event;
import org.urfu.spring2024.domain.Review;
import org.urfu.spring2024.domain.User;
import org.urfu.spring2024.extern.controller.UserController;
import org.urfu.spring2024.extern.dto.UserDTO;

import java.util.stream.Collectors;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserDTO> {
    public UserAssembler() {
        super(UserController.class, UserDTO.class);
    }

    @Override
    public UserDTO toModel(User user) {
        UserDTO userDTO = instantiateModel(user);

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setAdditionalInfo(user.getAdditionalInfo());
        userDTO.setReviewsIds(user.getReviews().stream()
                .map(Review::getId)
                .collect(Collectors.toList()));
        userDTO.setTrackedGamesIds(user.getTrackedGames().stream()
                .map(BoardGame::getId)
                .collect(Collectors.toList()));
        userDTO.setEventsAttendedIds(user.getEventsAttended().stream()
                .map(Event::getId)
                .collect(Collectors.toList()));

        return userDTO;
    }
}
