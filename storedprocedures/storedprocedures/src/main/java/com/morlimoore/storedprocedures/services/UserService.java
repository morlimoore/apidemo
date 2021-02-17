package com.morlimoore.storedprocedures.services;

import com.morlimoore.storedprocedures.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<User> save(User user);
//    ResponseEntity<User> getUserByEmail(String email);
    ResponseEntity<List<User>> getAll();
}