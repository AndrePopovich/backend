package com.bs.payload.response;

import com.bs.model.Ad;
import com.bs.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private String phone;
    private LocalDate dateOfRegistration;
    private List<Ad> ads;
}
