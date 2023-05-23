package com.fire.stockmarkets.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
    @Query(value = "SELECT MAX(d.date) FROM data AS d WHERE d.symbol = ?1 AND d.data_type = ?2 AND d.source = ?3", nativeQuery = true)
    String findLastBySymbolInType(String symbol, String dataType, Long sourceId);

    @Query(value = "SELECT MAX(data.date) FROM data JOIN companies ON data.company_id = companies.id JOIN markets ON data.market_id = markets.id WHERE symbol = ?1 AND stock_market = ?2 AND data_type = ?3 AND source_id = ?4", nativeQuery = true)
    String findLastBySymbolAndMarketInType(String symbol, String marketName, String dataType, Long sourceId);

    @Query(value = "SELECT data.* FROM data JOIN companies ON data.company_id = companies.id JOIN markets ON data.market_id = markets.id WHERE symbol = ?1 AND stock_market = ?2 AND data_type = ?3 ORDER BY data.date ASC LIMIT ?4", nativeQuery = true)
    List<Data> findDataBySymbolAndMarketInType(String symbol, String marketName, String dataType, Long count);
}
