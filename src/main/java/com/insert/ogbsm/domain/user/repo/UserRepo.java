package com.insert.ogbsm.domain.user.repo;

import com.insert.ogbsm.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {


}
