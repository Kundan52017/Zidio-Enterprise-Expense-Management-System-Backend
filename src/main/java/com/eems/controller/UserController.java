package com.eems.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eems.model.Role;
import com.eems.model.User;
import com.eems.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> userRequest) {
        String username = userRequest.get("username");
        String email = userRequest.get("email");
        String password = userRequest.get("password");
        String confirmPassword = userRequest.get("confirmPassword");

        if (!password.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.EMPLOYEE);  // Default role

        return ResponseEntity.ok(userService.signup(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String usernameOrEmail = loginRequest.get("usernameOrEmail");
        String password = loginRequest.get("password");

        if (usernameOrEmail == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username/Email and Password are required");
        }

        try {
            // Assuming the userService.login() returns a JWT token
            String token = userService.login(usernameOrEmail, password);

            // Return token as a map
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    }


