package com.example.clotheswebsite.controller;

import com.example.clotheswebsite.entity.Role;
import com.example.clotheswebsite.entity.User;
import com.example.clotheswebsite.repository.RoleRepository;
import com.example.clotheswebsite.service.UserService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam(name = "sortField", required = false, defaultValue = "") String sortField, @RequestParam(name = "sortBy",required = false, defaultValue = "") String sortBy){
        List<User> allUsers = userService.getAllUsers();
        if (sortField.equals("userId")){
            allUsers.sort(Comparator.comparing(User::getUserId));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        }
        else if (sortField.equals("username")){
            allUsers.sort(Comparator.comparing(User::getUsername));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        }
        else if (sortField.equals("fullname")){
            allUsers.sort(Comparator.comparing(User::getFullname));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        }
        else if (sortField.equals("email")){
            allUsers.sort(Comparator.comparing(User::getEmail));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        }
        else if (sortField.equals("createdAt")){
            allUsers.sort(Comparator.comparing(User::getCreatedAt));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        }
        else if (sortField.equals("updatedAt")){
            allUsers.sort(Comparator.comparing(User::getUpdatedAt));
            return sortBy.equals("asc") ? allUsers : allUsers.reversed();
        }
        return userService.getAllUsers();
    }


    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id){
        return userService.getUserById(id);
    }

    @PostMapping("/register-user")
    public ResponseEntity<User> registerUser(@RequestBody User newUser){
        User registeredUser;
        try {
            registeredUser = userService.registerUser(newUser);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            // Bắt và xử lý lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updateUser){
        User updatedUser = userService.updateUser(id, updateUser);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser); // Trả về người dùng đã được cập nhật nếu cập nhật thành công
        }
        return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy người dùng
    }
    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable("id") int id){
        userService.deleteUser(userService.getUserById(id));
    }

    @GetMapping("/search-users")
    public List<User> searchUsers(@RequestParam String keyword){
        return userService.searchUsers(keyword);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
