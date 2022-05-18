package com.javamaster.services.adapters;

import com.javamaster.entities.User;

import java.util.List;

public interface UserServiceAdapter {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    List<User> findAllManagers();

    List<User> findAllEngineers();
}
