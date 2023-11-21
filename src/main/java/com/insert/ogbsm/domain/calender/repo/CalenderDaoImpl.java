package com.insert.ogbsm.domain.calender.repo;

import com.insert.ogbsm.domain.calender.Type;
import com.insert.ogbsm.presentation.calender.dto.CalenderRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.insert.ogbsm.domain.calender.QCalender.calender;
import static com.insert.ogbsm.domain.user.QUser.user;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class CalenderDaoImpl implements CalenderDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Map<LocalDate, List<CalenderRes>> findMonthCalender(Integer year, Integer month, Short grade, Short classNumber) {
        return jpaQueryFactory
                .from(calender)
                .join(user)
                .on(calender.userId.eq(user.id))
                .where(
                        calender.date.year().eq(year)
                                .and(calender.type.eq(Type.SCHOOL)
                                        .and(calender.date.month().eq(month)))

                                .or(calender.type.eq(Type.GRADE)
                                        .and(calender.date.month().eq(month))
                                        .and(calender.grade.eq(grade)))

                                .or(calender.type.eq(Type.CLASS)
                                        .and(calender.date.month().eq(month))
                                        .and(calender.grade.eq(grade))
                                        .and(calender.classNumber.eq(classNumber))))

                .orderBy(calender.date.asc(), calender.type.desc(), calender.priority.desc())
                .transform(groupBy(calender.date).as(list(constructor(CalenderRes.class, calender, user))));
    }
}
