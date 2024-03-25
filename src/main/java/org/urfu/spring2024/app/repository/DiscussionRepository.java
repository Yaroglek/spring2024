package org.urfu.spring2024.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.Discussion;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Discussion findById(long id);
}
