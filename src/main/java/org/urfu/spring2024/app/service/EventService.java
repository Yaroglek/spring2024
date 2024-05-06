package org.urfu.spring2024.app.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.urfu.spring2024.app.repository.EventRepository;
import org.urfu.spring2024.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final BoardGameService boardGameService;
    private final UserService userService;
    private final EmailService emailService;

    /**
     * Сохранение в БД новой настольного мероприятия.
     *
     * @param event - объект мероприятия для сохранения в БД.
     * @return - сохраненное мероприятие.
     */
    public Event createEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Мероприятие не может быть null");
        }
        try {
            eventRepository.save(event);
            log.info("Создано мероприятие с ID {}", event.getId());
            return event;
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при сохранении нового мероприятия", e);
        }
    }

    /**
     * Поиск мероприятия в БД по его id.
     *
     * @param eventId - уникальный идентификатор для поиска мероприятия.
     * @return - мероприятие с указанным id.
     */
    @NonNull
    public Event getEventById(long eventId) {
        var searchedEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Мероприятие с ID " + eventId + " не найдено"));
        log.debug("Мероприятие с ID {} найдено", eventId);
        return searchedEvent;
    }

    /**
     * Удаление мероприятия из БД по его id.
     *
     * @param eventId - уникальный идентификатор для поиска мероприятия.
     */
    public void deleteEventById(long eventId) {
        eventRepository.deleteById(eventId);
        log.info("Мероприятие с ID {} удалено", eventId);
    }

    /**
     * Добавление игры в список привязанных к мероприятию.
     *
     * @param eventId - уникальный идентификатор для поиска мероприятия.
     * @param gameId  - уникальный идентификатор для поиска игры.
     */
    public void addBoardGame(long eventId, long gameId) {
        Event event = getEventById(eventId);
        BoardGame game = boardGameService.getBoardGameById(gameId);

        if (!event.getGames().contains(game)) {
            event.getGames().add(game);
            eventRepository.save(event);
            log.info("Игра {} (ID {}) теперь привязана к мероприятию {} (ID {})", game.getName(), gameId, event.getTitle(), eventId);
        }
    }

    /**
     * Удаление игры из списка привязанных к мероприятию.
     *
     * @param eventId - уникальный идентификатор для поиска мероприятия.
     * @param gameId  - уникальный идентификатор для поиска игры.
     */
    public void removeBoardGame(long eventId, long gameId) {
        Event event = getEventById(eventId);
        BoardGame game = boardGameService.getBoardGameById(gameId);

        if (event.getGames().contains(game)) {
            event.getGames().remove(game);
            eventRepository.save(event);
            log.info("Игра {} (ID {}) более не привязана к мероприятию {} (ID {})", game.getName(), gameId, event.getTitle(), eventId);
        }
    }

    /**
     * Добавление пользователя в список участников.
     *
     * @param eventId - уникальный идентификатор для поиска мероприятия.
     * @param userId  - уникальный идентификатор для поиска пользователя.
     */
    public void addParticipant(long eventId, long userId) {
        Event event = getEventById(eventId);
        User user = userService.getUserById(userId);

        if (!event.getParticipants().contains(user)) {
            event.getParticipants().add(user);
            eventRepository.save(event);
            log.info("Пользователь с ID {} теперь привязан к мероприятию {} (ID {})", userId, event.getTitle(), eventId);
        }
    }

    /**
     * Удаление пользователя из список участников.
     *
     * @param eventId - уникальный идентификатор для поиска мероприятия.
     * @param userId  - уникальный идентификатор для поиска пользователя.
     */
    public void removeParticipant(long eventId, long userId) {
        Event event = getEventById(eventId);
        User user = userService.getUserById(userId);

        if (event.getParticipants().contains(user)) {
            event.getParticipants().remove(user);
            eventRepository.save(event);
            log.info("Пользователь с ID {} более не привязан к мероприятию {} (ID {})", userId, event.getTitle(), eventId);
        }
    }

    /**
     * Изменение статуса мероприятия.
     *
     * @param eventId   - уникальный идентификатор для поиска мероприятия.
     * @param newStatus - новый статус.
     */
    public void changeEventStatus(long eventId, EventStatus newStatus) {
        Event event = getEventById(eventId);
        event.setStatus(newStatus);
        eventRepository.save(event);
        log.info("Мероприятие {} (ID {}) теперь имеет статус {}", event.getTitle(), eventId, newStatus);
    }

    /**
     * Изменение места проведения мероприятия.
     *
     * @param eventId     - уникальный идентификатор для поиска мероприятия.
     * @param newLocation - новое место проведения.
     */
    public void changeLocation(long eventId, String newLocation) {
        Event event = getEventById(eventId);
        event.setLocation(newLocation);
        eventRepository.save(event);
        log.info("Мероприятие {} (ID {}) теперь имеет новое место проведения - {}", event.getTitle(), eventId, newLocation);
    }

    /**
     * Еженедельная рассылка писем на электронную почту всем пользователям с уведомлением о предстоящих мероприятиях,
     * связанных с отслеживаемыми ими играми.
     */
    @Scheduled(cron = "@weekly")
    public void sendWeeklyEventNotifications() {
        LocalDate nextMonday = LocalDate.now().plusDays(8 - LocalDate.now().getDayOfWeek().getValue());

        List<Event> upcomingEvents = eventRepository.findByDatetimeBetween(
                LocalDateTime.of(nextMonday, LocalTime.MIN),
                LocalDateTime.of(nextMonday.plusDays(7), LocalTime.MIN)
        );

        if (upcomingEvents.isEmpty()) {
            return;
        }

        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            List<BoardGame> trackedGames = user.getTrackedGames();

            StringBuilder message = new StringBuilder("Уважаемый " + user.getUsername() + ",\n\n");
            message.append("На этой неделе по интересующим Вас играм запланированы следующие мероприятия:\n\n");

            List<Event> matchedEvents = new ArrayList<>();
            for (Event event : upcomingEvents) {
                boolean hasTrackedGame = event.getGames().stream()
                        .anyMatch(trackedGames::contains);
                if (hasTrackedGame) {
                    matchedEvents.add(event);
                }
            }

            if (!matchedEvents.isEmpty()) {
                for (Event event : matchedEvents) {
                    message.append("- ").append(event.getTitle()).append(" (")
                            .append(event.getDatetime().toString()).append(")\n");
                }
            }

            emailService.sendEmail(user.getEmail(), "Предстоящие мероприятия", message.toString());
        }
    }

    /**
     * Ежедневная рассылка напоминаний о скором начале мероприятий всем участникам этих мероприятий.
     */
    @Scheduled(cron = "@daily")
    public void sendEventReminder() {
        LocalDateTime tomorrow = LocalDate.now().plusDays(1).atStartOfDay();

        List<Event> tomorrowEvents = eventRepository.findByDatetimeBetween(
                tomorrow,
                tomorrow.withHour(23).withMinute(59).withSecond(59)
        );

        for (Event event : tomorrowEvents) {
            for (User participant : event.getParticipants()) {
                String message = String.format("Уважаемый %s + \n\n Завтра в %s начнется мероприятие %s, на которое вы ранее записались. Не забудьте присоединиться!",
                        participant.getUsername(), event.getTitle(), event.getDatetime());
                emailService.sendEmail(participant.getEmail(), "Предстоящее мероприятие", message);
            }
        }
    }
}
