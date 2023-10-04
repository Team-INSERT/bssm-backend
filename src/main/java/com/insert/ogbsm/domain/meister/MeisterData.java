package com.insert.ogbsm.domain.meister;

import com.insert.ogbsm.presentation.meister.dto.response.MeisterDetailResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MeisterData {

    @Id
    @Column(length = 10)
    private String studentId;

    @OneToOne
    @JoinColumn(name = "meisterId", nullable = false)
    private MeisterInfo meisterInfo;

    @Column(nullable = false)
    @ColumnDefault("0")
    private float score;

    @Column(nullable = false)
    @ColumnDefault("0")
    private float basicJobSkills;

    @Column(nullable = false)
    @ColumnDefault("0")
    private float professionalTech;

    @Column(nullable = false)
    @ColumnDefault("0")
    private float workEthic;

    @Column(nullable = false)
    @ColumnDefault("0")
    private float humanities;

    @Column(nullable = false)
    @ColumnDefault("0")
    private float foreignScore;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String scoreRawData;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int positivePoint;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int negativePoint;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String pointRawData;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public MeisterData(String studentId, MeisterInfo meisterInfo, float score, String scoreRawData, int positivePoint, int negativePoint, String pointRawData) {
        this.studentId = studentId;
        this.meisterInfo = meisterInfo;
        this.score = score;
        this.scoreRawData = scoreRawData;
        this.positivePoint = positivePoint;
        this.negativePoint = negativePoint;
        this.pointRawData = pointRawData;
    }

    public void setScores(MeisterDetailResponse score) {
        this.score = score.getScore();
        this.basicJobSkills = score.getBasicJobSkills();
        this.professionalTech = score.getProfessionalTech();
        this.workEthic = score.getWorkEthic();
        this.humanities = score.getHumanities();
        this.foreignScore = score.getForeignScore();
    }

    public void setScoreRawData(String scoreRawData) {
        this.scoreRawData = scoreRawData;
    }

    public void setPositivePoint(int positivePoint) {
        this.positivePoint = positivePoint;
    }

    public void setNegativePoint(int negativePoint) {
        this.negativePoint = negativePoint;
    }

    public void setPointRawData(String pointRawData) {
        this.pointRawData = pointRawData;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

}
