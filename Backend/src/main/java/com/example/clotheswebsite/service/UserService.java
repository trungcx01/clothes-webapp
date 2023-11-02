package com.example.clotheswebsite.service;

import com.example.clotheswebsite.entity.User;

import java.util.List;

public interface UserService{
    List<User> getAllUsers();
    User getUserById(int userId);
    User registerUser(User user);
    User updateUser(int userId, User user);
    void deleteUser(User user);
    List<User> searchUsers(String keyword);

}
