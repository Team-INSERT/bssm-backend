package com.insert.ogbsm.domain.meister.repository;

import com.insert.ogbsm.presentation.meister.dto.response.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.insert.ogbsm.domain.meister.QMeisterData.meisterData;
import static com.insert.ogbsm.domain.user.QStudent.student;

@RequiredArgsConstructor
public class MeisterDataDaoImpl implements MeisterDataDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MeisterRankingResponse> findByMeisterInfoStudentGradeOrderByScoreDesc(int grade) {
        return jpaQueryFactory
                .select(new QMeisterRankingResponse(
                        meisterData.score,
                        meisterData.positivePoint,
                        meisterData.negativePoint,
                        meisterData.modifiedAt,
                        new QMeisterStudentResponse(student.grade,
                                student.classNo,
                                student.studentNo,
                                student.name)))
                .from(meisterData)
                .leftJoin(student).on(meisterData.meisterInfo.email.eq(student.email))
                .where(student.grade.eq(grade)
                        .and(meisterData.meisterInfo.privateRanking.eq(false)))
                .orderBy(meisterData.score.desc())
                .fetch();
    }

    @Override
    public MeisterScoreResponse findAvgByGradeOfScores(int grade) {
        return jpaQueryFactory
                .select(new QMeisterScoreResponse(
                        meisterData.score.avg(),
                        meisterData.basicJobSkills.avg(),
                        meisterData.professionalTech.avg(),
                        meisterData.workEthic.avg(),
                        meisterData.humanities.avg(),
                        meisterData.foreignScore.avg(),
                        meisterData.positivePoint.avg(),
                        meisterData.negativePoint.avg()
                ))
                .from(meisterData)
                .leftJoin(student).on(meisterData.meisterInfo.email.eq(student.email))
                .where(student.grade.eq(grade))
                .fetch()
                .get(0);
    }

    @Override
    public MeisterScoreResponse findMaxByGradeOfScores(int grade) {
        return jpaQueryFactory
                .select(new QMeisterScoreResponse(
                        meisterData.score.max().doubleValue(),
                        meisterData.basicJobSkills.max().doubleValue(),
                        meisterData.professionalTech.max().doubleValue(),
                        meisterData.workEthic.max().doubleValue(),
                        meisterData.humanities.max().doubleValue(),
                        meisterData.foreignScore.max().doubleValue(),
                        meisterData.positivePoint.max().doubleValue(),
                        meisterData.negativePoint.max().doubleValue()
                ))
                .from(meisterData)
                .leftJoin(student).on(meisterData.meisterInfo.email.eq(student.email))
                .where(student.grade.eq(grade))
                .fetch()
                .get(0);
    }
}
