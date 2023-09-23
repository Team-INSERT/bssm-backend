package com.insert.ogbsm.domain.ber;

import com.insert.ogbsm.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private User reservationUser;

    @Builder
    public Ber(LocalDate reservation, Integer berNumber, User reservationUser) {
        this.reservationDate = reservation;
        this.berNumber = berNumber;
        this.reservationUser = reservationUser;
    }
}
