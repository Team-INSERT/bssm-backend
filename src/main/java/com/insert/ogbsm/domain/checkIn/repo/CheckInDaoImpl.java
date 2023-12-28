package com.insert.ogbsm.domain.checkIn.repo;

import com.insert.ogbsm.domain.checkIn.CheckIn;
import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.insert.ogbsm.domain.checkIn.QCheckIn.checkIn;
import static com.insert.ogbsm.domain.room.QRoom.room;

@RequiredArgsConstructor
public class CheckInDaoImpl implements CheckInDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CheckIn> findTodayCheckIn(Long userId) {
        LocalDateTime localDate = LocalDate.now().atStartOfDay();
        return Optional.ofNullable(jpaQueryFactory.select(checkIn)
                .from(checkIn)
                .where(checkIn.checkInTime.between(localDate,localDate.plusDays(1))
                        .and(checkIn.userId.eq(userId)))
                .fetchOne()
        );
    }

    @Override
    public List<Room> findAllTodayCheckIn(DormitoryType type) {
        LocalDate localDate = LocalDate.now();
        return jpaQueryFactory.select(room)
                .from(room)
                .where(room.yearSemester.eq(new YearSemester())
                        .and(room.dormitoryType.eq(type)))
                .fetch();
    }

    @Override
    public List<Room> findAllTodayCheckIn() {
        LocalDate localDate = LocalDate.now();
        return jpaQueryFactory.select(room)
                .from(room)
                .where(room.yearSemester.eq(new YearSemester()))
                .fetch();
    }


    @Override
    public List<CheckIn> findCheckInByRoomId(Long roomId) {
        LocalDate localDate = LocalDate.now();
        return jpaQueryFactory.select(checkIn)
                .from(checkIn)
                .where(checkIn.roomId.eq(roomId)
                        .and(checkIn.checkInTime.between(localDate.atStartOfDay(), localDate.atStartOfDay().plusDays(1))))
                .fetch();
    }
}
