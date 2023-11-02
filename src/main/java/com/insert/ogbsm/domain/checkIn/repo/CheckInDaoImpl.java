package com.insert.ogbsm.domain.checkIn.repo;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.presentation.checkIn.dto.CheckInRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.insert.ogbsm.domain.checkIn.QCheckIn.checkIn;
import static com.insert.ogbsm.domain.room.QRoom.room;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;

@RequiredArgsConstructor
public class CheckInDaoImpl implements CheckInDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CheckIn> findTodayCheckIn(LocalDateTime localDate, Long userId) {
        return Optional.ofNullable(
                jpaQueryFactory.select(checkIn)
                .from(checkIn)
                        .where(checkIn.checkInTime.between(localDate, localDate.plusDays(1))
                        .and(checkIn.userId.eq(userId)))
                .fetchOne());
    }

    @Override
    public List<CheckInRes> findAllTodayCheckIn(DormitoryType dormitoryType) {
        LocalDate localDate = LocalDate.now();

        return jpaQueryFactory.select(
                        constructor(
                                CheckInRes.class, room, list(checkIn)
                        ))
                .from(room)
                .leftJoin(checkIn)
                .on(room.id.eq(checkIn.roomId))
                .groupBy(room)
                .fetch();
    }


    @Override
    public List<CheckInRes> findAllTodayCheckIn() {
        LocalDate localDate = LocalDate.now();
        return jpaQueryFactory.select(constructor(CheckInRes.class, room, list(checkIn)))
                .from(room)
                .leftJoin(checkIn)
                .on(room.id.eq(checkIn.roomId))
                .where(
                        checkIn.checkInTime.dayOfYear().eq(localDate.getDayOfYear())
                                .and(checkIn.checkInTime.year().eq(localDate.getYear()))
                ).fetch();
    }
}
