package com.javamaster.repository;

import com.javamaster.entity.User;
import com.javamaster.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    List<User> findAllByRole(UserRole role);
}
