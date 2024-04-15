package org.urfu.spring2024.extern.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Category;
import org.urfu.spring2024.extern.controller.CategoryController;
import org.urfu.spring2024.extern.dto.CategoryDTO;

import java.util.stream.Collectors;

@Component
public class CategoryAssembler extends RepresentationModelAssemblerSupport<Category, CategoryDTO> {
    public CategoryAssembler() {
        super(CategoryController.class, CategoryDTO.class);
    }

    @Override
    public CategoryDTO toModel(Category category) {
        CategoryDTO categoryDTO = instantiateModel(category);

        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setGamesIds(category.getGames().stream()
                .map(BoardGame::getId)
                .collect(Collectors.toList()));

        return categoryDTO;
    }
}
