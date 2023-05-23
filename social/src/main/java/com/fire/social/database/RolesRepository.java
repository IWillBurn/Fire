package com.fire.social.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT * FROM roles AS r WHERE r.role_name = ?1", nativeQuery = true)
    Role findByName(String roleName);
}
