package org.urfu.spring2024.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(long id);

    void deleteById(long id);
}
