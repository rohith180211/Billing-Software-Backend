package com.example.billingsoftware.Service;

import com.example.billingsoftware.io.UserRequest;
import com.example.billingsoftware.io.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    String getUserRole(String email );
    List<UserResponse> readUsers();
    void deleteUser(String id);

}
