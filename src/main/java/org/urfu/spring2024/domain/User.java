package org.urfu.spring2024.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Сущность пользователя сервиса.
 */
@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * Уникальный иднетификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /**
     * Имя пользователя (nickname).
     */
    @Column(name = "user_name")
    @Setter
    private String username;

    /**
     * Электронная почта пользователя.
     */
    @Column(name = "user_email")
    @Setter
    private String email;

    /**
     * Пароль пользователя.
     */
    @Column(name = "user_password")
    @Setter
    private String password;

    /**
     * Дополнительная информация о пользователе.
     */
    @Column(name = "user_info")
    @Setter
    private String additionalInfo;

    /**
     * Все отзывы пользователя.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Review> reviews;

    /**
     * Отслеживаемые пользователем игры.
     */
    @ManyToMany
    @JoinTable(
            name = "user_game",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<BoardGame> trackedGames;

    /**
     * Посещенные пользователем мероприятия.
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "participants")
    private List<Event> eventsAttended;
}
