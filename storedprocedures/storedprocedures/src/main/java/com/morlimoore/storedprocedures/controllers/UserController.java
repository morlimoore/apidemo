package com.morlimoore.storedprocedures.controllers;

import com.morlimoore.storedprocedures.Dtos.UserDto;
import com.morlimoore.storedprocedures.models.User;
import com.morlimoore.storedprocedures.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody UserDto userDto) {
        return userService.save(modelMapper.map(userDto, User.class));
    }

    @GetMapping("/get/{lastName}")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getAll() {
        return userService.findAll();
    }
}