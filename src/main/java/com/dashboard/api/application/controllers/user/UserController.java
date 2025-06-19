package com.dashboard.api.application.controllers.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import com.dashboard.api.domain.user.User;
import com.dashboard.api.infrastructure.jwt.JwtUtil;
import com.dashboard.api.service.user.UserService;
import com.dashboard.api.service.user.dto.LoginRequest;
import com.dashboard.api.service.user.dto.UpdateUserInput;
import com.dashboard.api.service.user.dto.UserPresenter;
import com.dashboard.api.service.user.dto.UserWithTokenPresenter;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@EnableMethodSecurity
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserPresenter me() {
        return userService.me();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            String token = userService.login(request.username(), request.password());
            response.addHeader(HttpHeaders.SET_COOKIE, JwtUtil.MakeCookieString(token));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserPresenter> update(@RequestBody UpdateUserInput input, HttpServletResponse response) {
        UserWithTokenPresenter result = userService.update(input);
        if (result.token() != null)
            response.addHeader(HttpHeaders.SET_COOKIE, JwtUtil.MakeCookieString(result.token()));

        return ResponseEntity.ok(result.user());
    }
}
