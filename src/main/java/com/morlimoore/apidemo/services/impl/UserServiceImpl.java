package com.morlimoore.apidemo.services.impl;

import com.morlimoore.apidemo.dtos.UserUpdateDto;
import com.morlimoore.apidemo.entities.User;
import com.morlimoore.apidemo.repositories.UserRepository;
import com.morlimoore.apidemo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void updateUser(UserUpdateDto userUpdateDto, Long id) {
        User user = modelMapper.map(userUpdateDto, User.class);
        user.setId(id);
        saveUser(user);
    }

    @Override
    public void patchUser(UserUpdateDto userUpdateDto, Long id) {
        User user = getUser(id);
        if (!userUpdateDto.getFirstName().isBlank())
            user.setFirstName(userUpdateDto.getFirstName());
        if (!userUpdateDto.getLastName().isBlank())
            user.setLastName(userUpdateDto.getLastName());
        if (!userUpdateDto.getEmail().isBlank())
            user.setEmail(userUpdateDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> users = userRepository.findAll();
        User user = users.stream().
                filter(u -> u.getEmail().toLowerCase()
                        .equals(email.toLowerCase()))
                .findFirst().get();
        return user;
    }

    @Override
    public void deleteUserByEmail(String email) {
        userRepository.deleteById(getUserByEmail(email).getId());
    }
}