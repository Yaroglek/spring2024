package org.urfu.spring2024.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Категория настольной игры.
 */
@Entity
@Table(name = "categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /**
     * Уникальный идентификатор категории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    /**
     * Название категории.
     */
    @Column(name = "category_name")
    private String name;

    /**
     * Описание категории.
     */
    @Column(name = "category_description")
    private String description;

    /**
     * Список игр, подпадающих под данную категорию.
     */
    @ManyToMany(mappedBy = "categories")
    private List<BoardGame> games;
}
