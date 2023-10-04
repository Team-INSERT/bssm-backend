package com.insert.ogbsm.domain.meister.repository;

import com.insert.ogbsm.domain.meister.MeisterInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeisterInfoRepository extends JpaRepository<MeisterInfo, String> {
    Optional<MeisterInfo> findByEmail(String email);
}
