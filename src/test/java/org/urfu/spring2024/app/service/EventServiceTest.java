package org.urfu.spring2024.app.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.urfu.spring2024.app.repository.EventRepository;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Event;
import org.urfu.spring2024.domain.EventStatus;
import org.urfu.spring2024.domain.User;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class EventServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private BoardGameService boardGameService;
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    public void testCreateEvent() {
        Event event = Event.builder().id(1L).title("event1").build();

        when(eventRepository.save(event)).thenReturn(event);
        Event createdEvent = eventService.createEvent(event);

        assertNotNull(createdEvent.getId());
    }

    @Test
    public void testEventNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(null));
    }

    @Test
    public void testFindEventById() {
        Event event = Event.builder().id(1L).title("event1").build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        Event searchedEvent = eventService.getEventById(1L);

        assertNotNull(searchedEvent);
        assertEquals(1L, searchedEvent.getId());
    }

    @Test
    public void testEventNotFoundById() {
        when(eventRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> eventService.getEventById(3L));
    }

    @Test
    public void testDeleteEvent() {
        Event event = Event.builder().id(4L).title("event4").build();

        when(eventRepository.findById(4L)).thenReturn(Optional.of(event));
        eventService.deleteEventById(4L);

        verify(eventRepository, times(1)).deleteById(4L);
    }

    @Test
    public void testAddBoardGame() {
        Event event = Event.builder().id(1L).title("event1").games(new ArrayList<>()).build();
        BoardGame game = BoardGame.builder().id(1L).name("game1").build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(boardGameService.getBoardGameById(1L)).thenReturn(game);

        eventService.addBoardGame(1L, 1L);

        assertEquals(1, event.getGames().size());
    }

    @Test
    public void testRemoveBoardGame() {
        Event event = Event.builder().id(1L).title("event1").games(new ArrayList<>()).build();
        BoardGame game = BoardGame.builder().id(1L).name("game1").build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(boardGameService.getBoardGameById(1L)).thenReturn(game);

        eventService.removeBoardGame(1L, 1L);
        assertEquals(0, event.getGames().size());
    }

    @Test
    public void testAddParticipant() {
        Event event = (Event.builder().id(1L).title("event1")).participants(new ArrayList<>()).build();
        User user = User.builder().id(1L).username("user1").build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(userService.getUserById(1L)).thenReturn(user);

        eventService.addParticipant(1L, 1L);

        assertEquals(1, event.getParticipants().size());
    }

    @Test
    public void testRemoveParticipant() {
        Event event = Event.builder().id(1L).title("event1").participants(new ArrayList<>()).build();
        User user = User.builder().id(1L).username("user1").build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(userService.getUserById(1L)).thenReturn(user);

        eventService.removeParticipant(1L, 1L);
        assertEquals(0, event.getParticipants().size());
    }

    @Test
    public void testChangeStatus() {
        Event event = Event.builder().id(1L).title("event1").status(EventStatus.ONGOING).build();
        EventStatus newStatus = EventStatus.FINISHED;

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        eventService.changeEventStatus(1L, newStatus);

        assertEquals(event.getStatus(), EventStatus.FINISHED);
    }

    @Test
    public void testChangeLocation() {
        Event event = Event.builder().id(1L).title("event1").location("Мира 32").build();
        String newLocation = "Мира 19";

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        eventService.changeLocation(1L, newLocation);

        assertEquals(event.getLocation(), newLocation);
    }
}
