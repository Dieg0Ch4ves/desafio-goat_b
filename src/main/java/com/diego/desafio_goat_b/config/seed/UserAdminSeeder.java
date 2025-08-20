package com.diego.desafio_goat_b.config.seed;

import com.diego.desafio_goat_b.domain.entity.User;
import com.diego.desafio_goat_b.domain.enums.UserRole;
import com.diego.desafio_goat_b.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev"})
@RequiredArgsConstructor
public class UserAdminSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (users.existsByEmailIgnoreCase("admin@local")) return;
        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@local");
        admin.setPassword(encoder.encode("admin"));
        admin.setRole(UserRole.ADMIN);
        admin.setActive(true);
        users.save(admin);
    }
}