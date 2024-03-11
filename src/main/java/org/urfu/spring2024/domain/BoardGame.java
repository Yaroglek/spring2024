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
 * Сущность настольной игры.
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
    @Setter
    private String name;

    /**
     * Дата выхода игры.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "game_date")
    @Setter
    private Date releaseDate;

    /**
     * Производитель игры.
     */
    @Column(name = "game_manufacturer")
    @Setter
    private String manufacturer;

    /**
     * Описание игры.
     */
    @Column(name = "game_description")
    @Setter
    private String description;

    /**
     * Минимальный рекомендованный возраст.
     */
    @Column(name = "game_age")
    @Setter
    private String recommendedAge;

    /**
     * Рекомендованное количество игроков.
     */
    @Column(name = "game_players")
    @Setter
    private String amountOfPlayers;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_categories")
    @Setter
    private List<BoardGameCategories> categories;

    /**
     * Список отзывов на игру.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardGame")
    private List<Review> reviews;

    /**
     * Список мероприятий, привязанных к игре.
     */
    @ManyToMany(mappedBy = "games")
    private List<Event> events;

    /**
     * Список обусждений, привязанных к игре.
     */
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardGame")
//    private List<Discussion> discussions;
}
