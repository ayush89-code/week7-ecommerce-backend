package com.ecommerce.repository;

import com.ecommerce.model.entity.User;
import com.ecommerce.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Custom query methods
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    // Custom queries with @Query
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findAllByRole(@Param("role") Role role);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :fromDate")
    Long countUsersRegisteredSince(@Param("fromDate") LocalDateTime fromDate);
}
