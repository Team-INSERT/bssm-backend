package com.insert.ogbsm.domain.ber;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"reservationDate", "berNumber"}, name = "ber_unique"))
public class Ber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reservationDate;

    private Integer berNumber;

    private Long reservationUserId;

    private String reservationUsersName;

    @Builder
    public Ber(LocalDate reservation, Integer berNumber, Long reservationUserId, String reservationUsersName) {
        this.reservationDate = reservation;
        this.berNumber = berNumber;
        this.reservationUserId = reservationUserId;
        this.reservationUsersName = reservationUsersName;
    }
}
