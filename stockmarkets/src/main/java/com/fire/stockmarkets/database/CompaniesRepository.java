package com.fire.stockmarkets.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface CompaniesRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT * FROM companies AS c WHERE c.symbol = ?1 AND c.name = ?2", nativeQuery = true)
    Company isExist(String symbol, String name);
    @Query(value = "SELECT * FROM companies AS c WHERE c.symbol = ?1", nativeQuery = true)
    Company findBySymbol(String symbol);
    @Query(value = "SELECT * FROM companies AS c WHERE c.symbol LIKE ?1 AND NOT c.symbol LIKE ?2 AND NOT c.name LIKE ?3", nativeQuery = true)
    List<Company> findCompaniesBySymbolLikeString(String like, String notSymbolLike, String notNameLike);
    @Query(value = "SELECT * FROM companies AS c WHERE c.name LIKE ?1 AND NOT c.symbol LIKE ?2 AND NOT c.name LIKE ?3", nativeQuery = true)
    List<Company> findCompaniesByNameLikeString(String like, String notSymbolLike, String notNameLike);
    @Query(value = "SELECT * FROM companies AS c WHERE (c.symbol LIKE ?1 OR c.name LIKE ?1) AND NOT c.symbol LIKE ?2 AND NOT c.name LIKE ?3", nativeQuery = true)
    List<Company> findCompaniesByLikeString(String like, String notSymbolLike, String notNameLike);

}
