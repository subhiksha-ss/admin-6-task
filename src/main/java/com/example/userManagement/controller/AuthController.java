package com.example.userManagement.controller;

import com.example.userManagement.model.User;
import com.example.userManagement.repository.UserRepository;
import com.example.userManagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // DTO class for login request
    public static class LoginRequest {
        public String email;
        public String password;
    }

    // DTO class for login response
    public static class LoginResponse {
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email).orElse(null);

        if (user == null || !user.getPassword().equals(loginRequest.password)) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        if (user.getBlocked()) {
            return ResponseEntity.status(403).body("User is blocked");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}

