package com.morlimoore.storedprocedures.services.impl;

import com.morlimoore.storedprocedures.Dtos.UserDto;
import com.morlimoore.storedprocedures.models.User;
import com.morlimoore.storedprocedures.repositories.UserRepository;
import com.morlimoore.storedprocedures.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<User> save(User user) {
        User res = userRepository.save(user);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<User> getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email).get();
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}