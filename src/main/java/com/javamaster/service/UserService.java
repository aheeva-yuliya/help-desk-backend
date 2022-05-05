package com.javamaster.service;

import com.javamaster.entity.User;
import com.javamaster.entity.enums.UserRole;
import com.javamaster.repository.UserRepository;
import com.javamaster.service.adapters.UserServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserServiceAdapter {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User userEntity = findByEmail(email);
        System.out.println(userEntity);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    @Override
    public List<User> findAllManagers() {
        return userRepository.findAllByRole(UserRole.MANAGER);
    }

    @Override
    public List<User> findAllEngineers() {
        return userRepository.findAllByRole(UserRole.ENGINEER);
    }
}
