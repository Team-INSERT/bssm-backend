package com.insert.ogbsm.domain.room.repo;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.RoomMate;
import com.insert.ogbsm.domain.room.YearSemester;
import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.presentation.room.dto.RoomRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.insert.ogbsm.domain.room.QRoom.room;

@RequiredArgsConstructor
public class RoomDaoImpl implements RoomDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RoomRes> findAllByYearSemester() {
//        return jpaQueryFactory.from(room)
//                .select(constructor(RoomRes.class, room, list(constructor(UserSimpleWithNameRes.class, user))))
//                .leftJoin(room, user)
//                .where(room.yearSemester.eq(new YearSemester()))
//                .orderBy(room.dormitoryType.asc(), room.roomNumber.asc())
//                .fetch();
        return null;
    }

    @Override
    public Room findByUserId(Long userId) {
        RoomMate roomMate = new RoomMate();
        roomMate.addRoomMate(userId);
        List<Room> a = jpaQueryFactory.selectFrom(room)
                .from(room)
                .where(room.yearSemester.eq(new YearSemester()))
                .fetch();

        a = a.stream().filter(room1 -> room1.getRoomMate().equals(roomMate)).toList();
        return a.get(0);
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
