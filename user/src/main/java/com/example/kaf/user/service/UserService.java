package com.example.kaf.user.service;

import com.example.kaf.user.models.User;
import com.example.kaf.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void signup(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists with email: " + email);
        }


        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));  // Hash password before storing
        user.setEnabled(true);

        userRepository.save(user);
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        // Returning a dummy token for demonstration
        return "dummy-jwt-token-for-" + email;
    }

    public List<User> getAllUsers(){
       return userRepository.findAll();
    }

}
