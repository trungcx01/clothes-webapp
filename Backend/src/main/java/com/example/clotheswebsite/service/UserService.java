package com.example.clotheswebsite.service;

import com.example.clotheswebsite.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();

    UserEntity getUserById(int userId);

    UserEntity registerUser(UserEntity user);

    UserEntity updateUser(int userId, UserEntity user);

    void deleteUser(UserEntity user);

    List<UserEntity> searchUsers(String keyword);
    Boolean existsByUsername(String username);

}
