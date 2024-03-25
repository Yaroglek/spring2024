package org.urfu.spring2024.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    void deleteById(long id);
}
