package com.diego.desafio_goat_b.service;

import com.diego.desafio_goat_b.domain.entity.ActivationToken;
import com.diego.desafio_goat_b.domain.entity.User;
import com.diego.desafio_goat_b.dto.*;
import com.diego.desafio_goat_b.exception.TokenInvalidOrExpiredException;
import com.diego.desafio_goat_b.exception.UserNotActiveException;
import com.diego.desafio_goat_b.exception.UserNotFoundException;
import com.diego.desafio_goat_b.repository.ActivationTokenRepository;
import com.diego.desafio_goat_b.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ActivationTokenRepository activationTokenRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${frontend.url}")
    private String frontUrl;

    private static final String KEY_USER_NOT_FOUND = "User not found";

    public AuthenticationResponseDTO login(AuthenticationRequestDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var user = (User) auth.getPrincipal();

            if (Boolean.FALSE.equals(user.getActive())) {
                throw new UserNotActiveException("User is not active");
            }

            var token = tokenService.generateToken(user);
            return new AuthenticationResponseDTO(token);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Username or password invalid");
        }
    }

    public String register(RegisterRequestDTO data) {
        User existUser = repository.findByEmailIgnoreCase(data.email()).orElse(null);

        if (existUser != null) {
            throw new IllegalArgumentException("E-mail is already registered.");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(data.role());
        newUser.setActive(false);

        this.repository.save(newUser);

        String activationToken = UUID.randomUUID().toString();
        ActivationToken tokenEntity = new ActivationToken();
        tokenEntity.setUser(newUser);
        tokenEntity.setToken(activationToken);
        tokenEntity.setExpiresAt(LocalDateTime.now().plusHours(24));

        activationTokenRepository.save(tokenEntity);

        String activationLink = frontUrl + "/activate?token=" + activationToken;
        emailService.sendActivationEmail(newUser.getEmail(), activationLink);

        return "User has been successfully registered! Verify your e-mail for active.";
    }

    public String activate(String token) {
        ActivationToken activationToken = activationTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenInvalidOrExpiredException("Token invalid or expired"));

        if (activationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenInvalidOrExpiredException("Token expired. Request another.");
        }

        User user = activationToken.getUser();
        if (Boolean.TRUE.equals(user.getActive())) {
            return "User is already activated.";
        }

        user.setActive(true);
        repository.save(user);

        activationTokenRepository.delete(activationToken);

        return "User has been successfully activated!";
    }

    public UserResponseDTO getUserFromToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Authorization token is missing");
        }

        String username = tokenService.validateToken(token);

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Invalid token");
        }

        User user = repository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException(KEY_USER_NOT_FOUND));

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public UserResponseDTO getUserById(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(KEY_USER_NOT_FOUND));

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public String deleteUserById(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(KEY_USER_NOT_FOUND));

        repository.delete(user);
        return "User has been successfully deleted!";
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                .toList();
    }

    public String forgotPassword(ForgotPasswordRequestDTO data) {
        User user = repository.findByEmailIgnoreCase(data.email())
                .orElseThrow(() -> new UserNotFoundException(KEY_USER_NOT_FOUND));

        String resetToken = UUID.randomUUID().toString();
        ActivationToken tokenEntity = new ActivationToken();
        tokenEntity.setUser(user);
        tokenEntity.setToken(resetToken);
        tokenEntity.setExpiresAt(LocalDateTime.now().plusHours(24));
        activationTokenRepository.save(tokenEntity);

        String resetLink = frontUrl + "/reset-password?token=" + resetToken;
        emailService.sendResetPasswordEmail(user.getEmail(), resetLink);

        return "Recovery password e-mail has been successfully send!";
    }

    public String resetPassword(ResetPasswordRequestDTO data) {
        ActivationToken tokenEntity = activationTokenRepository.findByToken(data.token())
                .orElseThrow(() -> new TokenInvalidOrExpiredException("Token invalid or expired"));

        if (tokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenInvalidOrExpiredException("Token invalid. Request another.");
        }

        User user = tokenEntity.getUser();
        String encryptedPassword = passwordEncoder.encode(data.newPassword());
        user.setPassword(encryptedPassword);
        repository.save(user);

        activationTokenRepository.delete(tokenEntity);

        return "Password has been successfully reset!";
    }
}
