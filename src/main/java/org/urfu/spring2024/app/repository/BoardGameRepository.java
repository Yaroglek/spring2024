package org.urfu.spring2024.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.BoardGame;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {
    BoardGame findById(long id);
    void deleteById(long id);
}
