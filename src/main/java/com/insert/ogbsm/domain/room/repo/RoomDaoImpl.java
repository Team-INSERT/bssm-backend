package com.insert.ogbsm.domain.room.repo;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.room.RoomMate;
import com.insert.ogbsm.domain.room.YearSemester;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.insert.ogbsm.domain.room.QRoom.room;

@RequiredArgsConstructor
public class RoomDaoImpl implements RoomDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Room> findByUserId(Long userId) {
        RoomMate roomMate = new RoomMate();
        roomMate.addRoomMate(userId);
        List<Room> a = jpaQueryFactory.selectFrom(room)
                .from(room)
                .where(room.yearSemester.eq(new YearSemester()))
                .fetch();

        a = a.stream().filter(room1 -> room1.getRoomMate().equals(roomMate)).toList();
        try {
            return Optional.ofNullable(a.get(0));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
