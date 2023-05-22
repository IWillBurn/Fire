package com.fire.stockmarkets.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface SourcesRepository extends JpaRepository<Source, Long> {
    @Query(value = "SELECT * FROM sources AS s WHERE s.source = ?1", nativeQuery = true)
    Source findBySource(String source);
}
