package com.lamaison.auth.service.impl;

import com.lamaison.auth.dto.response.UserResponse;
import com.lamaison.auth.model.Role;
import com.lamaison.auth.repository.UserRepository;
import com.lamaison.auth.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Flux<UserResponse> getAllUsers() {
        return userRepository.findAll().map(this::toResponse);
    }

    @Override
    public Mono<UserResponse> getUserById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .map(this::toResponse);
    }

    @Override
    public Mono<UserResponse> updateRole(String id, Role nuevoRol) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    user.setRol(nuevoRol);
                    return userRepository.save(user);
                })
                .map(this::toResponse);
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(id);
    }

    private UserResponse toResponse(com.lamaison.auth.model.User user) {
        return new UserResponse(
                user.getId(),
                user.getNombre(),
                user.getCorreo(),
                user.getRol(),
                user.isActivo(),
                user.getCreadoEn()
        );
    }
}