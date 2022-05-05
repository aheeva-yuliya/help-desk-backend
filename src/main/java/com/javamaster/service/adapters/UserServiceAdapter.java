package com.javamaster.service.adapters;

import com.javamaster.entity.User;

import java.util.List;

public interface UserServiceAdapter {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    List<User> findAllManagers();

    List<User> findAllEngineers();
}
