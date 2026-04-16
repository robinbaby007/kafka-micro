package com.example.kaf.user.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
}
