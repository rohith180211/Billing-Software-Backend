package com.example.billingsoftware.Service.Impl;

import com.example.billingsoftware.Entity.UserEntity;
import com.example.billingsoftware.Repository.UserRepository;
import com.example.billingsoftware.Service.UserService;
import com.example.billingsoftware.io.UserRequest;
import com.example.billingsoftware.io.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse createUser(UserRequest request) {
        UserEntity user=convertToEntity(request);
        user=userRepository.save(user);
        return convertToResponse(user);
    }

    private UserResponse convertToResponse(UserEntity user) {
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .userId(user.getUserId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .role(user.getRole())
                .build();
    }

    private UserEntity convertToEntity(UserRequest request) {
        return UserEntity.builder().userId(UUID.randomUUID().toString())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole().toUpperCase())
                .name(request.getName())
                .build();


    }

    @Override
    public String getUserRole(String email) {
        UserEntity existingUser=userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return existingUser.getRole();
    }

    @Override
    public List<UserResponse> readUsers() {
        return userRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    @Override
    public void deleteUser(String id) {
        UserEntity user=userRepository.findByUserId(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
