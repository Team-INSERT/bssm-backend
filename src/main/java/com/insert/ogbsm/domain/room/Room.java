package com.insert.ogbsm.domain.room;

import com.insert.ogbsm.domain.room.type.DormitoryType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Room {

    public static boolean AllowAllocate = false;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;

    @Embedded
    private YearSemester yearSemester;

    private DormitoryType dormitoryType;

    @Embedded
    private RoomMate roomMate;

    @Builder
    public Room(String roomNumber, DormitoryType dormitoryType) {
        this.roomNumber = roomNumber;
        this.dormitoryType = dormitoryType;
        this.roomMate = new RoomMate();
        this.yearSemester = new YearSemester();
    }
}
