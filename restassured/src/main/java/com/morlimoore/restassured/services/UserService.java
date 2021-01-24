package com.morlimoore.restassured.services;

import com.morlimoore.restassured.dtos.UserUpdateDto;
import com.morlimoore.restassured.entities.User;

import java.util.List;

public interface UserService {

    List<User> listAllUsers();
    void saveUser(User user);
    User getUser(Long id);
    void updateUser(UserUpdateDto userUpdateDto, Long id);
    void patchUser(UserUpdateDto userUpdateDto, Long id);
    void deleteUser(Long id);
    User getUserByEmail(String email);
    void deleteUserByEmail(String email);
}