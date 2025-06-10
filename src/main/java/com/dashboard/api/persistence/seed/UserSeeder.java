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
@Order(3)
public class UserSeeder implements CommandLineRunner {
    private final UserService userService;
    private final AuthorityRepository authorityRepository;

    @Value("${seeding.user.username}")
    private String adminUsername;

    @Value("${seeding.user.password}")
    private String adminPassword;

    public UserSeeder(UserService userService, AuthorityRepository authorityRepository) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Set<Authority> role = Set.of(authorityRepository.findById("ROLE_USER").orElseThrow());
        User user = new User(adminUsername, adminPassword, role);
        try {
            userService.registerUser(user);
        } catch (EntityExistsException e) {
            System.out.println("Sample user already seeded");
            return;
        }

        System.out.println("Seeded sample user");
    }

}
