package com.example.clotheswebsite.controller;

import com.example.clotheswebsite.config.JWTGenerator;
import com.example.clotheswebsite.dto.AuthResponseDTO;
import com.example.clotheswebsite.dto.LoginDTO;
import com.example.clotheswebsite.message.ResponseMessage;
import com.example.clotheswebsite.repository.RoleRepository;
import com.example.clotheswebsite.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTGenerator jwtGenerator;


    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponseDTO(token));

    }

//    @PostMapping("/api/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if(auth != null) {
//
//            // Clear security context
//            SecurityContextHolder.clearContext();
//
//            // Invalidate token in response
//            response.setHeader("Authorization","Bearer null");
//        }
//
//        return ResponseEntity.ok("Logout successful");
//
//    }


//    @PostMapping("register")
//    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
//        if (userRepository.existsByUsername(registerDto.getUsername())) {
//            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        UserEntity user = new UserEntity();
//        user.setUsername(registerDto.getUsername());
//        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
//
//        Role roles = roleRepository.findByName("USER").get();
//        user.setRoles(Collections.singletonList(roles));
//
//        userRepository.save(user);
//
//        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
//    }
}