package org.urfu.spring2024.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discussion_id")
    private Long id;

    /**
     * Пользователь, создавший ветку (топикстартер).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User topicStarter;

    /**
     * Игра, привязанная к обсуждению (не может быть не привязанно ни к одной игре).
     */
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;

    /**
     * Дата и время создания ветки.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "discussion_dttm")
    private LocalDateTime createdDttm;

    /**
     * Тема обсуждения.
     */
    @Column(name = "discussion_topic")
    @Getter
    private String topic;

    /**
     * Список сообщений в ветке.
     */
    @OrderBy
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "discussion")
    private List<Message> messages;
}
