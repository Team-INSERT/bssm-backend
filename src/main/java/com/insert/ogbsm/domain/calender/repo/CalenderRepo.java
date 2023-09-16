package com.insert.ogbsm.domain.calender.repo;

import com.insert.ogbsm.domain.calender.Calender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalenderRepo extends JpaRepository<Calender, Long> {
    @Query("select c from Calender c where c.date.month = :month and c.date.day = :day")
    List<Calender> findByDate(Long month, Long day);
}
