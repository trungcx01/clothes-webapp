package com.example.clotheswebsite.controller;

import com.example.clotheswebsite.entity.Role;
import com.example.clotheswebsite.entity.UserEntity;
import com.example.clotheswebsite.repository.RoleRepository;
import com.example.clotheswebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    
    @GetMapping("/api/users")
    public List<UserEntity> getAllUsers(@RequestParam(name = "sortField", required = false, defaultValue = "") String sortField, @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy) {
        List<UserEntity> allUsers = userService.getAllUsers();
        if (sortField.equals("userId")) {
            allUsers.sort(Comparator.comparing(UserEntity::getUserId));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        } else if (sortField.equals("username")) {
            allUsers.sort(Comparator.comparing(UserEntity::getUsername));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        } else if (sortField.equals("fullname")) {
            allUsers.sort(Comparator.comparing(UserEntity::getFullname));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        } else if (sortField.equals("email")) {
            allUsers.sort(Comparator.comparing(UserEntity::getEmail));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        } else if (sortField.equals("createdAt")) {
            allUsers.sort(Comparator.comparing(UserEntity::getCreatedAt));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        } else if (sortField.equals("updatedAt")) {
            allUsers.sort(Comparator.comparing(UserEntity::getUpdatedAt));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        }
        return userService.getAllUsers();
    }


    @GetMapping("/api/user/{id}")
    public UserEntity getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/api/register-user")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity newUser) {
        UserEntity registeredUser;
        try {
            registeredUser = userService.registerUser(newUser);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            // Bắt và xử lý lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/api/update-user/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable int id, @RequestBody UserEntity updateUser) {
        UserEntity updatedUser = userService.updateUser(id, updateUser);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser); // Trả về người dùng đã được cập nhật nếu cập nhật thành công
        }
        return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy người dùng
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/api/delete-user/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(userService.getUserById(id));
    }

    @GetMapping("/api/search-users")
    public List<UserEntity> searchUsers(@RequestParam String keyword) {
        return userService.searchUsers(keyword);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
