package com.insert.ogbsm.domain.bamboo.repo;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AllowedBambooRepo extends JpaRepository<AllowedBamboo, Long> {

    Optional<AllowedBamboo> findByBamboo(Bamboo bamboo);

    @Query("select a from AllowedBamboo a order by a.bamboo.createdAt desc")
    Page<AllowedBamboo> findAllByCreateAtDesc(Pageable pageable);
}
