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

}
