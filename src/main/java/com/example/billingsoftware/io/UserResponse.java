package com.example.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String role;
}
