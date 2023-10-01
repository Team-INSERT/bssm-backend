package com.insert.ogbsm.domain.timeTable.repo;

import com.insert.ogbsm.presentation.timeTable.dto.QTimeTableValueRes;
import com.insert.ogbsm.presentation.timeTable.dto.TimeTableValueRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.insert.ogbsm.domain.timeTable.QTimeTable.timeTable;

@RequiredArgsConstructor
public class TimeTableDaoImpl implements TimeTableDao {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TimeTableValueRes> findByGradeAndClassNumber(int grade, int classNumber, List<LocalDate> startAndEndOfWeek) {
        return jpaQueryFactory
                .select(new QTimeTableValueRes(timeTable.pk.period, timeTable.subject, timeTable.pk.date))
                .from(timeTable)
                .where(timeTable.pk.grade.eq(grade)
                        .and(timeTable.pk.classNumber.eq(classNumber)))
                .where(timeTable.pk.date.between(startAndEndOfWeek.get(0), startAndEndOfWeek.get(1)))
                .orderBy(timeTable.pk.date.asc(), timeTable.pk.period.asc())
                .fetch();
    }
}
