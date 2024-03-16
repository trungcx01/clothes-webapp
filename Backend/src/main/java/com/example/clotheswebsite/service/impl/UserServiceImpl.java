package com.example.clotheswebsite.service.impl;

import com.example.clotheswebsite.entity.UserEntity;
import com.example.clotheswebsite.repository.UserRepository;
import com.example.clotheswebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserEntity registerUser(UserEntity newUser) {
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public UserEntity updateUser(int userId, UserEntity updateUser) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setEmail(updateUser.getEmail());
            user.setFullname(updateUser.getFullname());
            user.setGender(updateUser.getGender());
            user.setUsername(updateUser.getUsername());
//            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            user.setUpdatedAt(LocalDateTime.now());
            user.setRoles(updateUser.getRoles());
            userRepository.save(user);
            return user; // Trả về người dùng sau khi đã được cập nhật
        }
        return null; // Trả về null nếu không tìm thấy người dùng
    }

    @Override
    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }

    @Override
    public List<UserEntity> searchUsers(String keyword) {
        return userRepository.findByUsernameContainingOrFullnameContainingOrEmailContaining(keyword, keyword, keyword);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
