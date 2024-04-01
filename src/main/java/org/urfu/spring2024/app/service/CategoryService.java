package org.urfu.spring2024.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.urfu.spring2024.app.repository.CategoryRepository;
import org.urfu.spring2024.domain.Category;

/**
 * Сервисный класс для работы с категориями игр.
 */
@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    /**
     * Сохранение в БД новой категории.
     *
     * @param category - объект категории для сохранения в БД.
     * @return - сохраненная категория.
     */
    public Category createCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Категория не может быть null");
        }
        try {
            categoryRepository.save(category);
            log.info("Создана категория с ID {}", category.getId());
            return category;
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при сохранении новой категории", e);
        }
    }

    /**
     * Поиск категории в БД по её id.
     *
     * @param categoryID - уникальный идентификатор для поиска категории.
     * @return - категория с указанным id.
     */
    public Category getCategoryByID(long categoryID) {
        Category searchedCategory = categoryRepository.findById(categoryID);
        if (searchedCategory == null) {
            throw new IllegalArgumentException("Категория с ID " + categoryID + " не найдена");
        } else {
            log.info("Категория с ID {} найдена", categoryID);
            return searchedCategory;
        }
    }

    /**
     * Удаление категории из БД по её id.
     *
     * @param categoryID - уникальный идентификатор для поиска категории.
     */
    public void deleteCategoryByID(long categoryID) {
        Category searchedCategory = categoryRepository.findById(categoryID);
        if (searchedCategory == null) {
            throw new IllegalArgumentException("Категория с ID " + categoryID + " не найдена");
        } else {
            categoryRepository.deleteById(categoryID);
            log.info("Категория с ID {} удалена", categoryID);
        }
    }
}
