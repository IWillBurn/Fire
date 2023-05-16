package com.fire.stockmarkets.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketsRepository extends JpaRepository<Market, Long> {

}
