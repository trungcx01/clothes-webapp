package com.example.clotheswebsite.service;

import com.example.clotheswebsite.entity.User;
import com.example.clotheswebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User registerUser(User newUser) {
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(int userId, User updateUser) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setEmail(updateUser.getEmail());
            user.setFullname(updateUser.getFullname());
            user.setGender(updateUser.getGender());
            user.setUsername(updateUser.getUsername());
            user.setPassword(updateUser.getPassword());
            user.setUpdatedAt(LocalDateTime.now());
            user.setRole(updateUser.getRole());
            userRepository.save(user);
            return user; // Trả về người dùng sau khi đã được cập nhật
        }
        return null; // Trả về null nếu không tìm thấy người dùng
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.findByUsernameContainingOrFullnameContainingOrEmailContaining(keyword, keyword, keyword);
    }
}
