package com.example.kaf.user.service;

import com.example.kaf.user.models.User;
import com.example.kaf.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void signup(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists with email: " + email);
        }


        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(true);

        userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // In a real application, use passwordEncoder.matches(password, user.getPassword())
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        // Returning a dummy token for demonstration
        return "dummy-jwt-token-for-" + email;
    }

    public List<User> getAllUsers(){
       return userRepository.findAll();
    }

}
