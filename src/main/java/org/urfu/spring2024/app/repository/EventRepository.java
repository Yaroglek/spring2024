package org.urfu.spring2024.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findById(long id);
}
