package com.morlimoore.apidemo.services;

import com.morlimoore.apidemo.dtos.UserUpdateDto;
import com.morlimoore.apidemo.entities.User;

import java.util.List;

public interface UserService {

    List<User> listAllUsers();
    void saveUser(User user);
    User getUser(Long id);
    void updateUser(UserUpdateDto userUpdateDto, Long id);
    void patchUser(UserUpdateDto userUpdateDto, Long id);
    void deleteUser(Long id);
}