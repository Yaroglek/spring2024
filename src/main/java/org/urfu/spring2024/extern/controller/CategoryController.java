package org.urfu.spring2024.extern.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urfu.spring2024.app.service.CategoryService;
import org.urfu.spring2024.domain.Category;
import org.urfu.spring2024.extern.assembler.CategoryAssembler;
import org.urfu.spring2024.extern.dto.CategoryDTO;

import java.util.ArrayList;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;
    private CategoryAssembler categoryAssembler;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category newCategory = Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .games(new ArrayList<>())
                .build();

        categoryService.createCategory(newCategory);

        return new ResponseEntity<>(categoryAssembler.toModel(newCategory), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getUserById(@PathVariable long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryAssembler.toModel(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    //TODO
}