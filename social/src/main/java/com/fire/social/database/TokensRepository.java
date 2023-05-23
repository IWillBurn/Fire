package com.fire.social.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokensRepository extends JpaRepository<Token, Long> {
    @Query(value = "SELECT tokens.* FROM tokens JOIN roles ON tokens.role_id = roles.id JOIN users ON tokens.user_id = users.id WHERE users.login = ?1 AND roles.role_name = ?2", nativeQuery = true)
    Token findToken(String login, String roleName);

    @Query(value = "SELECT * FROM tokens AS t WHERE t.token = ?1", nativeQuery = true)
    Token findToken(String token);

    @Query(value = "SELECT * FROM tokens AS t WHERE t.user_id = ?1", nativeQuery = true)
    List<Token> findAll(Long userId);
}
