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
     * @param categoryId - уникальный идентификатор для поиска категории.
     * @return - категория с указанным id.
     */
    public Category getCategoryById(long categoryId) {
        var searchedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Категория с ID " + categoryId + " не найдена"));
        log.debug("Категория с ID {} найдена", categoryId);
        return searchedCategory;
    }

    /**
     * Удаление категории из БД по её id.
     *
     * @param categoryId - уникальный идентификатор для поиска категории.
     */
    public void deleteCategoryById(long categoryId) {
        categoryRepository.deleteById(categoryId);
        log.info("Категория с ID {} удалена", categoryId);
    }
}
