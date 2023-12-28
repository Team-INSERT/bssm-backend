package com.insert.ogbsm.domain.checkIn.repo;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepo extends JpaRepository<CheckIn, Long>, CheckInDao {
}
