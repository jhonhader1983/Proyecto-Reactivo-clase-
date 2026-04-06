package com.lamaison.auth.repository;

import com.lamaison.auth.model.Role;
import com.lamaison.auth.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByCorreo(String correo);
    Mono<Boolean> existsByCorreo(String correo);
    Flux<User> findByRol(Role rol);
    Flux<User> findByActivo(boolean activo);
}