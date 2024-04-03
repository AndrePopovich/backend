package com.bs.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String firstname;
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastname;
    @NotBlank
    @Email
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
    @NotBlank
    private String phone;
    @NotBlank
    private LocalDate dateOfRegistration;
}
