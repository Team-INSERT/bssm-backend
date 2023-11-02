package com.insert.ogbsm.domain.room.repo;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.presentation.room.dto.RoomRes;
import com.insert.ogbsm.presentation.user.dto.UserSimpleWithNameRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.insert.ogbsm.domain.room.QRoom.room;
import static com.insert.ogbsm.domain.user.QUser.user;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;

@RequiredArgsConstructor
public class RoomDaoImpl implements RoomDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RoomRes> findByYearSemester(YearSemester yearSemester) {
        return jpaQueryFactory.from(room)
                .select(constructor(RoomRes.class, room, list(constructor(UserSimpleWithNameRes.class, user))))
                .join(room.roomMate.roomMateIds, user.id)
                .where(room.yearSemester.eq(yearSemester))
                .orderBy(room.dormitoryType.asc(), room.roomNumber.asc())
                .fetch();
    }


    @Override
    public Optional<Room> findByAllData(String roomNumber, DormitoryType dormitoryType, YearSemester yearSemester) {
        return Optional.ofNullable(jpaQueryFactory.select(room)
                .from(room)
                .where(
                        room.roomNumber.eq(roomNumber)
                                .and(room.dormitoryType.eq(dormitoryType)
                                        .and(room.yearSemester.eq(yearSemester)))
                ).fetchOne());
    }

}
