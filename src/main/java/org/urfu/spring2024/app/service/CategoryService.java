package org.urfu.spring2024.app.service;

import org.springframework.stereotype.Service;
import org.urfu.spring2024.domain.Category;

@Service
public class CategoryService {
    public Category getCategoryByID(long categoryID) {
        return new Category();
    }
}
