package org.urfu.spring2024.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.BoardGame;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {
    Optional<BoardGame> findById(long id);
    void deleteById(long id);
    List<BoardGame> findAllByNameContainsIgnoreCase(String name);

    //TODO Остальные запросы для поиска игр по определнным параметрам
//    @Query("SELECT bg FROM BoardGame bg JOIN bg.categories cat WHERE cat = :category")
//    List<BoardGame> findAllByCategory(@Param("category") Category category);
//    List<BoardGame> findALLByRecommendedAgeLessThanEqual(int age);
//    List<BoardGame> findALLByAmountOfPlayersEquals(int players);
}
