package com.javamaster.controller.test;

import com.javamaster.entity.User;
import com.javamaster.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tests")
public class ObservableController {
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return ResponseEntity.ok("deleted");
    }
}
