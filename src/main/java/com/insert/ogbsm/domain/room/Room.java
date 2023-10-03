package com.insert.ogbsm.domain.room;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String roomNumber;

    private DormitoryType dormitoryType;

    @Embedded
    private RoomMate roomMate;

    public Room(String roomNumber, DormitoryType dormitoryType) {
        this.roomNumber = roomNumber;
        this.dormitoryType = dormitoryType;
        this.roomMate = new RoomMate();
    }
}
