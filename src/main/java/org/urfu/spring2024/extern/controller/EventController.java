package org.urfu.spring2024.extern.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urfu.spring2024.app.service.BoardGameService;
import org.urfu.spring2024.app.service.EventService;
import org.urfu.spring2024.app.service.ManufacturerService;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Event;
import org.urfu.spring2024.domain.EventStatus;
import org.urfu.spring2024.extern.assembler.BoardGameAssembler;
import org.urfu.spring2024.extern.assembler.EventAssembler;
import org.urfu.spring2024.extern.dto.BoardGameDTO;
import org.urfu.spring2024.extern.dto.EventDTO;
import org.urfu.spring2024.extern.dto.FiltersDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {
    private EventService eventService;
    private EventAssembler eventAssembler;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid EventDTO eventDTO) {

        Event newEvent = Event.builder()
                .title(eventDTO.getTitle())
                .description(eventDTO.getDescription())
                .datetime(eventDTO.getDatetime())
                .location(eventDTO.getLocation())
                .games(new ArrayList<>())
                .participants(new ArrayList<>())
                .build();

        eventService.createEvent(newEvent);

        return new ResponseEntity<>(eventAssembler.toModel(newEvent), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventByID(@PathVariable long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(eventAssembler.toModel(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventByID(@PathVariable long id) {
        eventService.deleteEventById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{eventId}/games/{gameId}")
    public ResponseEntity<Void> addGame(@PathVariable long eventId, @PathVariable long gameId) {
        eventService.addBoardGame(eventId, gameId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventId}/games/{gameId}")
    public ResponseEntity<Void> removeGame(@PathVariable long eventId, @PathVariable long gameId) {
        eventService.removeBoardGame(eventId, gameId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{eventId}/games/{userId}")
    public ResponseEntity<Void> addUser(@PathVariable long eventId, @PathVariable long userId) {
        eventService.addParticipant(eventId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventId}/games/{userId}")
    public ResponseEntity<Void> removeUser(@PathVariable long eventId, @PathVariable long userId) {
        eventService.removeParticipant(eventId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{eventId}/location")
    public ResponseEntity<Void> changeLocation(@PathVariable long eventId, @RequestParam String location) {
        eventService.changeLocation(eventId, location);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{eventId}/status")
    public ResponseEntity<Void> changeDatetime(@PathVariable long eventId, @RequestParam String status) {
        eventService.changeEventStatus(eventId, EventStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }
}
