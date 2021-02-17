package com.morlimoore.storedprocedures.services.impl;

import com.morlimoore.storedprocedures.Dtos.UserDto;
import com.morlimoore.storedprocedures.dao.AbstractDao;
import com.morlimoore.storedprocedures.dao.UserDao;
import com.morlimoore.storedprocedures.models.User;
//import com.morlimoore.storedprocedures.repositories.UserRepository;
import com.morlimoore.storedprocedures.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    public UserServiceImpl(@Qualifier("userDao") AbstractDao<User> dao) {
        super(dao);
    }

    @Override
    public ResponseEntity<User> save(User user) {
        User res = super.create(user);
        return ResponseEntity.ok(res);
    }

//    @Override
//    public ResponseEntity<User> getUserByEmail(String email) {
//        User user = userRepository.getUserByEmail(email).get();
//        return ResponseEntity.ok(user);
//    }
//
    @Override
    public ResponseEntity<List<User>> getAll() {
        UserDao userDao = (UserDao) dao;
        List<User> users = userDao.findAll();
        return ResponseEntity.ok(users);
    }
}