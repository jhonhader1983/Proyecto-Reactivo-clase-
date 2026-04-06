package com.lamaison.auth.repository;

import com.lamaison.auth.model.PasswordResetToken;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PasswordResetTokenRepository extends ReactiveMongoRepository<PasswordResetToken, String> {
    Mono<PasswordResetToken> findByToken(String token);
    Mono<Void> deleteByUserId(String userId);
}