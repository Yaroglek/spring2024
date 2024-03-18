package org.urfu.spring2024.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Производитель настольных игр.
 */
@Entity
@Table(name = "manufacturers")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer {

    /**
     * Уникальный идентификатор производителя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "manufacturer_id")
    private Long id;

    /**
     * Название компании производителя.
     */
    @Column(name = "manufacturer_name")
    private String name;

    /**
     * Информация о производителе.
     */
    @Column(name = "manufacturer_description")
    @Setter
    private String description;

    /**
     * Список всех игр производителя.
    */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manufacturer")
    private List<BoardGame> games;
}
