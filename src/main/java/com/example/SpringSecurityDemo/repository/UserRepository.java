package com.example.SpringSecurityDemo.repository;

import com.example.SpringSecurityDemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

//    boolean existByEmail(String email);
//
//    boolean existByUsername(String username);
}
