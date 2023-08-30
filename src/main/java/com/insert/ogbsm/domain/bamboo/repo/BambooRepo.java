package com.insert.ogbsm.domain.bamboo.repo;

import com.insert.ogbsm.domain.bamboo.Bamboo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BambooRepo extends JpaRepository<Bamboo, Long> {

    List<Bamboo> findAllByIsAllow(boolean isAllow);

}
