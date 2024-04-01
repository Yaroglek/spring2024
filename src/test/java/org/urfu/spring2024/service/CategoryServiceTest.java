package org.urfu.spring2024.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.urfu.spring2024.app.repository.CategoryRepository;
import org.urfu.spring2024.app.service.CategoryService;
import org.urfu.spring2024.domain.Category;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testCreateCategory() {
        Category category = Category.builder().id(1L).name("category1").build();

        when(categoryRepository.save(category)).thenReturn(category);
        Category createdCategory = categoryService.createCategory(category);

        assertNotNull(createdCategory.getId());
    }

    @Test
    public void testBoardGameNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(null));
    }

    @Test
    public void testFindBoardGameByID() {
        Category category = Category.builder().id(1L).name("category1").build();

        when(categoryRepository.findById(1L)).thenReturn(category);
        Category searchedCategory = categoryService.getCategoryByID(1L);

        assertNotNull(searchedCategory);
        assertEquals(1L, searchedCategory.getId());
    }

    @Test
    public void testBoardGameNotFoundByID() {
        when(categoryRepository.findById(5L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> categoryService.getCategoryByID(5L));
    }

    @Test
    public void testDeleteBoardGame() {
        Category category = Category.builder().id(2L).name("category2").build();

        when(categoryRepository.findById(2L)).thenReturn(category);
        categoryService.deleteCategoryByID(2L);

        verify(categoryRepository, times(1)).deleteById(2L);
    }
}
