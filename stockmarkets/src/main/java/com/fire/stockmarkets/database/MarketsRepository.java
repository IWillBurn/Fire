package com.fire.stockmarkets.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MarketsRepository extends JpaRepository<Market, Long> {
    @Transactional
    @Query(value = "SELECT * FROM markets AS m WHERE m.stock_market = ?1", nativeQuery = true)
    Market findByName(String name);
}
