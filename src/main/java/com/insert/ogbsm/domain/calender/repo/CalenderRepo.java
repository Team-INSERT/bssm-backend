package com.insert.ogbsm.domain.calender.repo;

import com.insert.ogbsm.domain.calender.Calender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalenderRepo extends JpaRepository<Calender, Long>, CalenderDao {
    List<Calender> findByDate(LocalDate date);


}
