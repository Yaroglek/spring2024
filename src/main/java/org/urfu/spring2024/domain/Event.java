package org.urfu.spring2024.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Сущность мероприятия.
 */
@Entity
@Table(name = "events")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    /**
     * Уникальный идентификатор мероприятия.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    /**
     * Название мероприятия.
     */
    @Column(name = "event_title")
    @Setter
    private String title;

    /**
     * Дата проведения мероприятия.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "event_date")
    @Setter
    private Date date;

    /**
     * Место проведения мероприятия.
     */
    @Column(name = "event_location")
    @Setter
    private String location;

    /**
     * Статус мероприятия.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "event_status")
    @Setter
    private EventStatus status;

    /**
     * Список привязанных к мероприятию настольных игр.
     */
    @ManyToMany
    @JoinTable(
            name = "event_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<BoardGame> games;

    /**
     * Список участников мероприятия.
     */
    @ManyToMany
    @JoinTable(
            name = "event_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<User> participants;
}

