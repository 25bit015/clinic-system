package com.clinic.system.service;

import com.clinic.system.dto.AuthResponse;
import com.clinic.system.dto.LoginRequest;
import com.clinic.system.dto.RegisterRequest;
import com.clinic.system.entity.Role;
import com.clinic.system.entity.User;
import com.clinic.system.repository.RoleRepository;
import com.clinic.system.repository.UserRepository;
import com.clinic.system.security.AppUserPrincipal;
import com.clinic.system.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST, "Username already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());

        Set<String> roleNames = (request.roles() == null || request.roles().isEmpty())
                ? Set.of("RECEPTIONIST")
                : request.roles();

        Set<Role> roles = roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Invalid role: " + roleName)))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(new AppUserPrincipal(saved));
        return new AuthResponse(token, saved.getUsername(), roleNames);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        AppUserPrincipal principal = (AppUserPrincipal) auth.getPrincipal();
        Set<String> roles = principal.getAuthorities().stream()
                .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toSet());

        return new AuthResponse(jwtService.generateToken(principal), principal.getUsername(), roles);
    }
}
