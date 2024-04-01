package org.urfu.spring2024.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "game_dttm")
    private LocalDate releaseDate;

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

    @ManyToMany
    @JoinTable(
            name = "game_category",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

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
