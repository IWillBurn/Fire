package com.fire.security.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<Permission, Long> {
    @Query(value = "SELECT * FROM permissions AS p WHERE p.perm_name = ?1", nativeQuery = true)
    Permission findByName(String permName);
}
