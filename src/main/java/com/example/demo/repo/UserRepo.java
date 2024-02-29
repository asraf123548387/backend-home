package com.example.demo.repo;

import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);

    List<User> findByUserNameContainingIgnoreCase(String search);

    Optional<User> findByEmail(String username);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.otp = :otp")
    User findByOtp(@Param("otp") String otp);

    List<User> findALlByRolesContaining(String roleAdmin);

    List<User> findByUserNameContainingIgnoreCaseAndRolesContainingIgnoreCase(String search, String roleUser);
}
