package com.insert.ogbsm.domain.timeTable.repo;

import com.insert.ogbsm.domain.timeTable.TimeTable;
import com.insert.ogbsm.domain.timeTable.TimeTablePk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepo extends JpaRepository<TimeTable, TimeTablePk>, TimeTableDao {
}
