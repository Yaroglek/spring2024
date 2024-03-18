package org.urfu.spring2024.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Ветка обсуждения игры (тема, топик).
 */
@Entity
@Table(name = "discussions")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Discussion {
    /**
     * Уникальный идентификатор обсуждения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "discussion_id")
    private Long id;

    /**
     * Пользователь, создавший ветку (топикстартер).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Игра, привязанная к обсуждению (не может быть не привязанного к какой-либо обсуждения).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private BoardGame game;

    /**
     * Дата и время создания ветки.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "discussion_dttm")
    private LocalDateTime dateTime;

    /**
     * Тема обсуждения.
     */
    @Column(name = "discussion_topic")
    @Getter
    private String topic;

    /**
     * Список сообщений в ветке.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "discussion")
    private List<Message> messages;
}
