package com.clinic.system.config;

import com.clinic.system.entity.Role;
import com.clinic.system.entity.User;
import com.clinic.system.repository.RoleRepository;
import com.clinic.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Set<String> DEFAULT_ROLES = Set.of(
            "ADMIN", "RECEPTIONIST", "DOCTOR", "NURSE", "LAB_TECH", "PHARMACIST", "CASHIER"
    );

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        DEFAULT_ROLES.forEach(roleName -> roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(roleName);
                    return roleRepository.save(role);
                }));

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setFullName("System Administrator");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.getRoles().add(roleRepository.findByName("ADMIN").orElseThrow());
            userRepository.save(admin);
        }
    }
}
