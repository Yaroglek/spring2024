package org.urfu.spring2024.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findById(long id);
}