package com.insert.ogbsm.domain.checkIn;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime checkInTime;
    private Long userId;
    private Long roomId;

    @Builder
    public CheckIn(LocalDateTime checkInTime, Long userId, Long roomId) {
        this.checkInTime = checkInTime;
        this.userId = userId;
        this.roomId = roomId;
    }

}
