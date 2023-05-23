package com.fire.security.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users AS u WHERE u.email = ?1", nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM users AS u WHERE u.login = ?1 AND u.password = ?2", nativeQuery = true)
    User authorization(String login, String password);
}
