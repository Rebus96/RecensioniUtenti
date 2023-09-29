package com.example.recensioniutenti.Config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {
    private String token;
    private Long id;
    private String email;
    private String firstname;
    private List<String> roles;

    public UserInfoResponse( String username, List<String> roles, String token) {
        this.roles = roles;
        this.email = username;
        this.token = token;

    }
}