package org.urfu.spring2024.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Сообщение в ветке обсуждения {@link Discussion}
 */
@Entity
@Table(name = "messages")
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Message {
    /**
     * Уникальный идентификатор сообщения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    /**
     * Пользователь, написавший сообщение.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    /**
     * Ветка, в которой было оставлено сообщение.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discussion_id")
    private Discussion discussion;


    /**
     * Дата и время создания сообщения.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "message_dttm")
    private LocalDateTime createdDTTM;

    /**
     * Содержимое сообщения
     */
    @Column(name = "message_content")
    @Setter
    private String content;
}
