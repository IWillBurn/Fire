package com.fire.stockmarkets.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrenciesRepository extends JpaRepository<Currency, Long> {
    @Query(value = "SELECT * FROM currencies AS c WHERE c.currency = ?1", nativeQuery = true)
    Currency findByName(String name);

    @Query(value = "SELECT * FROM currencies", nativeQuery = true)
    List<Currency> getAll();
}
