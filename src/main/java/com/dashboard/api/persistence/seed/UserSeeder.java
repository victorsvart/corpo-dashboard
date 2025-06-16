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
    private String userUsername;

    @Value("${seeding.user.password}")
    private String userPassword;

    public UserSeeder(UserService userService, AuthorityRepository authorityRepository) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String profilePic = "https://pm1.aminoapps.com/7258/5520799cf0539b408bd8abee0a14d3a492ee5107r1-753-753v2_hq.jpg";
        Set<Authority> role = Set.of(authorityRepository.findById("ROLE_USER").orElseThrow());
        User user = new User(userUsername, userPassword, "User", "Da Silva", profilePic, role);
        try {
            userService.register(user);
        } catch (EntityExistsException e) {
            System.out.println("Sample user already seeded");
            return;
        }

        System.out.println("Seeded sample user");
    }

}
