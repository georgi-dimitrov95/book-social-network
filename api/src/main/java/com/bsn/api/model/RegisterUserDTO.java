package com.bsn.api.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {

    @NotBlank(message = "First name is mandatory")
    private String firstname;

    @NotBlank(message = "Last name is mandatory")
    private String lastname;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 50, message = "Password should be between 8 and 50 characters long")
    private String password;
}
