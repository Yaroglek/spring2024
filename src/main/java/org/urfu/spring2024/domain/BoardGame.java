package org.urfu.spring2024.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Настольная игра.
 */
@Entity
@Table(name = "games")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardGame {

    /**
     * Уникальный идентификатор игры.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private Long id;

    /**
     * Название игры.
     */
    @Column(name = "game_name")
    private String name;

    /**
     * Дата выхода игры.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "game_dttm")
    private LocalDateTime dateTime;

    /**
     * Производитель игры.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_manufacturer")
    private Manufacturer manufacturer;

    /**
     * Описание игры.
     */
    @Column(name = "game_description")
    private String description;

    /**
     * Минимальный рекомендованный возраст.
     */
    @Column(name = "game_age")
    private Integer recommendedAge;

    /**
     * Рекомендованное количество игроков.
     */
    @Column(name = "game_players")
    private Integer amountOfPlayers;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_categories")
    private List<BoardGameCategories> categories;

    /**
     * Список отзывов на игру.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "boardGame")
    private List<Review> reviews;

    /**
     * Список мероприятий, привязанных к игре.
     */
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "games")
    private List<Event> events;

    /**
     * Список обусждений, привязанных к игре.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "boardGame")
    private List<Discussion> discussions;
}
