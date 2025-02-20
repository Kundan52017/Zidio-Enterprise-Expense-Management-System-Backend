package com.eems.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eems.model.User;
import com.eems.repository.UserRepository;
import com.eems.security.JwtUtil;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // ✅ Encrypt password
        return userRepository.save(user);
    }

    public String login(String usernameOrEmail, String password) {
        if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
            throw new RuntimeException("Username or Email cannot be null or empty");
        }

        Optional<User> optionalUser = userRepository.findByUsername(usernameOrEmail);
        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByEmail(usernameOrEmail);
        }

        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found: " + usernameOrEmail));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }

}
