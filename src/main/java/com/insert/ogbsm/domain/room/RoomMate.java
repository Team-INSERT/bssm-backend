package com.insert.ogbsm.domain.room;

import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Embeddable
public class RoomMate {

    private Set<Long> roomMateIds;

    public RoomMate() {
        this.roomMateIds = new HashSet<>();
    }

    public void addRoomMate(Long userId) {
        if (roomMateIds.size() >= 2)
            throw new BsmException(ErrorCode.AlreadyFullRoomMate);
        roomMateIds.add(userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomMate roomMate = (RoomMate) o;

        return getRoomMateIds() != null ? getRoomMateIds().containsAll(roomMate.getRoomMateIds()) : roomMate.getRoomMateIds() == null;
    }

    @Override
    public int hashCode() {
        return getRoomMateIds() != null ? getRoomMateIds().hashCode() : 0;
    }
}
