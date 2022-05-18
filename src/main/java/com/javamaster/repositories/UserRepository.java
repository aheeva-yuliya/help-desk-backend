package com.javamaster.repositories;

import com.javamaster.entities.User;
import com.javamaster.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    List<User> findAllByRole(UserRole role);
}
