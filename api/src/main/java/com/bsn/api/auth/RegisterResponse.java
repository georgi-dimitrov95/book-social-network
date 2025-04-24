package com.bsn.api.auth;

import com.bsn.api.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    public RegisterResponseDTO(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }
}
