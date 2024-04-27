package org.urfu.spring2024.extern.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.urfu.spring2024.domain.*;
import org.urfu.spring2024.extern.controller.EventController;
import org.urfu.spring2024.extern.dto.EventDTO;

import java.util.stream.Collectors;

@Component
public class EventAssembler extends RepresentationModelAssemblerSupport<Event, EventDTO> {
    public EventAssembler() {
        super(EventController.class, EventDTO.class);
    }

    @Override
    public EventDTO toModel(Event event) {
        EventDTO eventDTO = instantiateModel(event);

        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setDatetime(event.getDatetime());
        eventDTO.setLocation(event.getLocation());
        eventDTO.setGamesIds(event.getGames().stream()
                .map(BoardGame::getId)
                .collect(Collectors.toList()));
        eventDTO.setParticipants(event.getParticipants().stream()
                .map(User::getId)
                .collect(Collectors.toList()));

        return eventDTO;
    }
}
