package com.example.recensioniutenti.Config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank
    private String firstname;
    @NotBlank
    private String email;

    private Set<String> role;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
