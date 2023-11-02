package com.example.clotheswebsite.service;

import com.example.clotheswebsite.entity.User;
import com.example.clotheswebsite.helper.ExcelHelper;
import com.example.clotheswebsite.repository.RoleRepository;
import com.example.clotheswebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    public void save(MultipartFile file){
        try{
            List<User> users = ExcelHelper.excelToUsers(file.getInputStream(), roleRepository);
            users.forEach(user -> userService.registerUser(user));
        }catch (IOException e){
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}
