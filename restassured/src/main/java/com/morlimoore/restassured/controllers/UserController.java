package com.morlimoore.restassured.controllers;

import com.morlimoore.restassured.dtos.UserCreateDto;
import com.morlimoore.restassured.dtos.UserUpdateDto;
import com.morlimoore.restassured.entities.User;
import com.morlimoore.restassured.payload.ApiResponse;
import com.morlimoore.restassured.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.morlimoore.restassured.util.ResponseUtil.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userService.listAllUsers();
        if(users.size() == 0) {
            return createResponse(null, "There are no users present", null, HttpStatus.BAD_REQUEST, null);
        }
        return createResponse(users, "Successful", null, HttpStatus.OK, null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long id) {
        try {
            User user = userService.getUser(id);
            return createResponse(List.of(user), "Successful", null, HttpStatus.OK, null);
        } catch(NoSuchElementException e) {
            return createResponse(null, "User does not exist", null, HttpStatus.NOT_FOUND, null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserCreateDto userCreateDto) {
        User user = modelMapper.map(userCreateDto, User.class);
        try {
            userService.saveUser(user);
        } catch(Exception e) {
           return createResponse(null, "User already exists", null, HttpStatus.BAD_REQUEST, null);
        }
        return createResponse(null, "User added successfully", null, HttpStatus.CREATED, null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateDto userUpdateDto,
                                             @PathVariable Long id) {
        try {
            userService.getUser(id);
        } catch(NoSuchElementException e) {
            return createResponse(null, "User does not exist", null, HttpStatus.NOT_FOUND, null);
        }
        userService.updateUser(userUpdateDto, id);
        return createResponse(null, "User updated successfully", null, HttpStatus.OK, null);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<ApiResponse> patchUser(@RequestBody UserUpdateDto userUpdateDto,
                                            @PathVariable Long id) {
        try {
            userService.getUser(id);
        } catch(NoSuchElementException e) {
            return createResponse(null, "User does not exist", null, HttpStatus.NOT_FOUND, null);
        }
        userService.patchUser(userUpdateDto, id);
        return createResponse(null, "User updated successfully", null, HttpStatus.OK, null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try {
            userService.getUser(id);
        } catch(NoSuchElementException e) {
            return createResponse(null, "User does not exist", null, HttpStatus.NOT_FOUND, null);
        }
        userService.deleteUser(id);
        return createResponse(null, "User deleted successfully", null, HttpStatus.OK, null);
    }
}