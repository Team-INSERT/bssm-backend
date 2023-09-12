package com.insert.ogbsm.domain.auth.repo;

import com.insert.ogbsm.domain.auth.RefreshToken;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableRedisRepositories
public interface RefreshTokenRepo extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findById(String authId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
