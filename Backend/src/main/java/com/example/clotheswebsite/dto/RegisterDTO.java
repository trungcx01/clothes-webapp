package com.example.clotheswebsite.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private int gender;
}
