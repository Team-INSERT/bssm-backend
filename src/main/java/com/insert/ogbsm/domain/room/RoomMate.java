package com.insert.ogbsm.domain.room;

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
        roomMateIds.add(userId);
    }

}
