package org.urfu.spring2024.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 * Сущность отзыва.
 */
@Entity
@Table(name = "reviews")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    /**
     * Уникальный идентификатор отзыва.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    /**
     * Содержимое отзыва.
     */
    @Column(name = "review_text")
    @Setter
    private String text;

    /**
     * Дата и время публикации отзыва.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "review_dttm")
    private LocalDateTime createdDttm;

    /**
     * Автор отзыва.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Игра, на которую отзыв был оставлен.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;
}

