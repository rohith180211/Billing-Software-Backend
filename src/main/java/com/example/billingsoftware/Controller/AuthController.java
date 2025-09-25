package com.example.billingsoftware.Controller;


import com.example.billingsoftware.Service.Impl.AppUserDetailsService;
import com.example.billingsoftware.Service.UserService;
import com.example.billingsoftware.io.AuthRequest;
import com.example.billingsoftware.io.AuthResponse;
import com.example.billingsoftware.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;
    private final JWTUtil jwtUtil;
    private final UserService user
    private final UserService userService;
    Service;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        authenticate(authRequest.getEmail(),authRequest.getPassword());
        final UserDetails userDetails = appUserDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwtToken=jwtUtil.generateToken(userDetails);
        String role=userService.getUserRole(authRequest.getEmail());
        return new AuthResponse(authRequest.getEmail(),role,jwtToken);
    }

    private void authenticate(String email, String password) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }
        catch (DisabledException e){
            throw new DisabledException("User is disabled");
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @PostMapping("/encode")
    public String encodePassword(@RequestBody Map<String, String> request) {
        return passwordEncoder.encode(request.get("password"));
    }
}
