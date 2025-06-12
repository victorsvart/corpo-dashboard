package com.dashboard.api.application.controllers.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import com.dashboard.api.domain.user.User;
import com.dashboard.api.service.user.UserService;

@RestController
@EnableMethodSecurity
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/auth_test_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> authTestAdmin() {
        return Map.of("response", "hello world - admin");
    }

    @GetMapping("/auth_test_user")
    @PreAuthorize("hasRole('USER')")
    public Map<String, String> authTestUser() {
        return Map.of("response", "hello world - user");
    }

    @PostMapping("/login")
    public IdToken login(@RequestParam String username, @RequestParam String password) {
        String token = userService.login(username, password);
        return new IdToken(token);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    public static class IdToken {
        private String id_token;

        public IdToken() {
        }

        public IdToken(String idToken) {
            this.id_token = idToken;
        }

        public String getId_token() {
            return id_token;
        }

        public void setId_token(String id_token) {
            this.id_token = id_token;
        }
    }
}
