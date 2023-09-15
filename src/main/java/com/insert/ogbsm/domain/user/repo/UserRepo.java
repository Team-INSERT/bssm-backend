package com.insert.ogbsm.domain.user.repo;

import com.insert.ogbsm.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String id);
}
