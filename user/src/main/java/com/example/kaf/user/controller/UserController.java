package com.example.kaf.user.controller;

import com.example.kaf.user.models.AuthResponse;
import com.example.kaf.user.models.LoginRequest;
import com.example.kaf.user.models.SignupRequest;
import com.example.kaf.user.models.User;
import com.example.kaf.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        try {
            userService.signup(request.getEmail(), request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(null, "User registered successfully", request.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(null, e.getMessage(), request.getEmail()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = userService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(
                    new AuthResponse(token, "Login successful", request.getEmail())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, "Invalid email or password", request.getEmail()));
        }
    }


    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers() {
        // This is a placeholder. In a real application, you would return a list of users.
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


}
