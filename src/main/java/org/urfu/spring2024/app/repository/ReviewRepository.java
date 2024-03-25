package org.urfu.spring2024.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findById(long id);
}
