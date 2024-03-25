package org.urfu.spring2024.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urfu.spring2024.domain.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Manufacturer findById(long id);
}
