package com.insert.ogbsm.domain.user.repo;

import com.insert.ogbsm.domain.user.User;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByIdIn(Collection<Long> ids);
    Optional<User> findByEmail(String id);
}
