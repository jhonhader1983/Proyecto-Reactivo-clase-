package com.lamaison.auth.service;

import com.lamaison.auth.dto.response.UserResponse;
import com.lamaison.auth.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserResponse> getAllUsers();
    Mono<UserResponse> getUserById(String id);
    Mono<UserResponse> updateRole(String id, Role nuevoRol);
    Mono<Void> deleteUser(String id);
}