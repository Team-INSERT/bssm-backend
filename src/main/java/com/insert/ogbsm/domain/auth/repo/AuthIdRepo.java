package com.insert.ogbsm.domain.auth.repo;

import com.insert.ogbsm.domain.auth.AuthId;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableRedisRepositories
public interface AuthIdRepo extends CrudRepository<AuthId, String> {
    Optional<AuthId> findByAuthId(String authId);

}