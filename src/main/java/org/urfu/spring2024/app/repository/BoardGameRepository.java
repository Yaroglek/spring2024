package org.urfu.spring2024.app.repository;

import com.querydsl.core.types.Predicate;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.BoardGame;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long>, QuerydslPredicateExecutor<BoardGame> {
    Optional<BoardGame> findById(long id);
    void deleteById(long id);

    @Override
    @NonNull
    List<BoardGame> findAll(@NonNull Predicate predicate);
}
