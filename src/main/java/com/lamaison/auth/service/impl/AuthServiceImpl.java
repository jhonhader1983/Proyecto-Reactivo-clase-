package com.lamaison.auth.service.impl;

import com.lamaison.auth.config.JwtUtil;
import com.lamaison.auth.dto.request.LoginRequest;
import com.lamaison.auth.dto.request.PasswordResetRequest;
import com.lamaison.auth.dto.request.RegisterRequest;
import com.lamaison.auth.dto.response.AuthResponse;
import com.lamaison.auth.model.PasswordResetToken;
import com.lamaison.auth.model.Role;
import com.lamaison.auth.model.User;
import com.lamaison.auth.repository.PasswordResetTokenRepository;
import com.lamaison.auth.repository.UserRepository;
import com.lamaison.auth.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordResetTokenRepository tokenRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<AuthResponse> register(RegisterRequest request) {
        return userRepository.existsByCorreo(request.getCorreo())
                .flatMap(exists -> {
                    if (exists) return Mono.error(new RuntimeException("El correo ya está registrado"));
                    User user = new User();
                    user.setNombre(request.getNombre());
                    user.setCorreo(request.getCorreo());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setRol(Role.CLIENTE);
                    user.setActivo(true);
                    user.setCreadoEn(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .map(user -> new AuthResponse(
                        jwtUtil.generateToken(user.getCorreo(), user.getRol().name()),
                        user.getNombre(),
                        user.getCorreo(),
                        user.getRol()
                ));
    }

    @Override
    public Mono<AuthResponse> login(LoginRequest request) {
        return userRepository.findByCorreo(request.getCorreo())
                .switchIfEmpty(Mono.error(new RuntimeException("Credenciales inválidas")))
                .flatMap(user -> {
                    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        return Mono.error(new RuntimeException("Credenciales inválidas"));
                    }
                    user.setUltimoLogin(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .map(user -> new AuthResponse(
                        jwtUtil.generateToken(user.getCorreo(), user.getRol().name()),
                        user.getNombre(),
                        user.getCorreo(),
                        user.getRol()
                ));
    }

    @Override
    public Mono<Void> requestPasswordReset(PasswordResetRequest request) {
        return userRepository.findByCorreo(request.getCorreo())
                .switchIfEmpty(Mono.error(new RuntimeException("Correo no encontrado")))
                .flatMap(user -> {
                    PasswordResetToken resetToken = new PasswordResetToken();
                    resetToken.setUserId(user.getId());
                    resetToken.setToken(UUID.randomUUID().toString());
                    resetToken.setExpiracion(LocalDateTime.now().plusHours(1));
                    resetToken.setUsado(false);
                    return tokenRepository.save(resetToken);
                })
                .then();
    }

    @Override
    public Mono<Void> resetPassword(String token, String newPassword) {
        return tokenRepository.findByToken(token)
                .switchIfEmpty(Mono.error(new RuntimeException("Token inválido")))
                .flatMap(resetToken -> {
                    if (resetToken.isUsado() || resetToken.getExpiracion().isBefore(LocalDateTime.now())) {
                        return Mono.error(new RuntimeException("Token expirado o ya usado"));
                    }
                    return userRepository.findById(resetToken.getUserId())
                            .flatMap(user -> {
                                user.setPassword(passwordEncoder.encode(newPassword));
                                resetToken.setUsado(true);
                                return userRepository.save(user).then(tokenRepository.save(resetToken));
                            });
                })
                .then();
    }
}