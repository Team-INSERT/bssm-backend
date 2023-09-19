package com.insert.ogbsm.domain.calender.repo;

import com.insert.ogbsm.domain.calender.Type;
import com.insert.ogbsm.presentation.calender.dto.CalenderGraphRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderRes;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.insert.ogbsm.domain.calender.QCalender.calender;
import static com.insert.ogbsm.domain.user.QUser.user;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class CalenderDaoImpl implements CalenderDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CalenderGraphRes get(Integer month, Short grade, Short classNumber) {
        return new CalenderGraphRes(
                findByClass(month, grade, classNumber),
                findByGrade(month, grade),
                findBySchool(month)
        );
    }

    private List<CalenderRes> findBySchool(Integer month) {
        return findTemplate(
                calender.date.month.eq(month)
                        .and(calender.type.eq(Type.SCHOOL)),
                calender.date.day.asc()
        );
    }

    private List<CalenderRes> findByGrade(Integer month, Short grade) {
        return findTemplate(
                calender.date.month.eq(month)
                        .and(calender.type.eq(Type.GRADE))
                        .and(calender.grade.eq(grade)),
                calender.date.day.asc());
    }

    private List<CalenderRes> findByClass(Integer month, Short grade, Short classNumber) {
        return findTemplate(
                calender.date.month.eq(month)
                        .and(calender.type.eq(Type.CLASS))
                        .and(calender.grade.eq(grade)
                                .and(calender.classNumber.eq(classNumber))),
                calender.date.day.asc(), calender.priority.desc()
        );
    }

    public List<CalenderRes> findTemplate(BooleanExpression where, OrderSpecifier<Integer>... orderBy) {
        return jpaQueryFactory
                .from(calender)
                .select(constructor(CalenderRes.class, calender, user))
                .join(user)
                .on(calender.userId.eq(user.id))
                .where(where)
                .orderBy(orderBy)
                .fetch();
    }
}