package com.insert.ogbsm.domain.room.repo;

import com.insert.ogbsm.domain.room.Room;

import java.util.Optional;

public interface RoomDao {
    Optional<Room> findByUserId(Long userId);
}
