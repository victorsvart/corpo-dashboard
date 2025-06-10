package com.dashboard.api.persistence.seed;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.dashboard.api.domain.authority.Authority;
import com.dashboard.api.domain.user.User;
import com.dashboard.api.persistence.jpa.authority.AuthorityRepository;
import com.dashboard.api.service.user.UserService;

import jakarta.persistence.EntityExistsException;

@Component
@Order(2)
public class AdminUserSeeder implements CommandLineRunner {
    // private final UserRepository userRepository;
    // private final AuthorityRepository authorityRepository;
    // private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthorityRepository authorityRepository;

    @Value("${seeding.admin.username}")
    private String adminUsername;

    @Value("${seeding.admin.password}")
    private String adminPassword;

    public AdminUserSeeder(UserService userService, AuthorityRepository authorityRepository) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) {
        Set<Authority> roles = Set.of(
                authorityRepository.findById("ROLE_USER").orElseThrow(),
                authorityRepository.findById("ROLE_ADMIN").orElseThrow());

        User user = new User(adminUsername, adminPassword, roles);
        try {
            userService.registerUser(user);
        } catch (EntityExistsException e) {
            System.out.println("Admin user already seeded");
            return;
        }

        System.out.println("Seeded admin user");
    }

}
