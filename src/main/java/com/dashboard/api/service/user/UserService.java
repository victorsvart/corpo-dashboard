package com.dashboard.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dashboard.api.domain.user.User;
import com.dashboard.api.infrastructure.jwt.TokenProvider;
import com.dashboard.api.persistence.jpa.user.UserRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User registerUser(User user) throws EntityExistsException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new EntityExistsException("username is taken!");
        }

        if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            user.setDefaultAuthority();
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public String login(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                    password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return tokenProvider.makeToken(authentication);
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Invalid username or password");
        }
    }
}
