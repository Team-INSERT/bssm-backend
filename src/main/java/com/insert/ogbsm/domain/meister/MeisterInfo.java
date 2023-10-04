package com.insert.ogbsm.domain.meister;

import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeisterInfo {

    @Id
    @Column(length = 10)
    private String studentId;

    @Column(length = 32)
    private String email;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean loginError;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean privateRanking;

    @Column(nullable = false)
    private LocalDateTime lastPrivateDate;

    @Builder
    public MeisterInfo(String studentId, boolean loginError, boolean privateRanking, LocalDateTime lastPrivateDate, String email) {
        this.studentId = studentId;
        this.loginError = loginError;
        this.privateRanking = privateRanking;
        this.lastPrivateDate = lastPrivateDate;
        this.email = email;
    }

    public void setPrivateRanking(boolean privateRanking) {
        this.privateRanking = privateRanking;
    }

    public void setLastPrivateDate(LocalDateTime lastPrivateDate) {
        this.lastPrivateDate = lastPrivateDate;
    }

    public void setLoginError(boolean loginError) {
        this.loginError = loginError;
    }

    public void privateCheck(String email) {
        if (privateRanking && !email.equals(this.email)) {
            throw new BsmException(ErrorCode.MEISTER_INFO_PRIVATE);
        }
    }

}