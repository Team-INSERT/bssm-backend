package com.insert.ogbsm.domain.bamboo.repo;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AllowedBambooRepo extends JpaRepository<AllowedBamboo, Long> {

    Optional<AllowedBamboo> findByBamboo(Bamboo bamboo);

}
