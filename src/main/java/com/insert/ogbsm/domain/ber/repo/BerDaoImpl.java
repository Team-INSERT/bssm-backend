package com.insert.ogbsm.domain.ber.repo;

import com.insert.ogbsm.presentation.ber.dto.BerRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.insert.ogbsm.domain.ber.QBer.ber;
import static com.insert.ogbsm.domain.user.QUser.user;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class BerDaoImpl implements BerDao {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BerRes> findByReservationDate(LocalDate reservationDate) {
        return jpaQueryFactory.from(ber)
                .select(constructor(BerRes.class, ber, user))
                .join(user)
                .on(ber.reservationUserId.eq(user.id))
                .where(ber.reservationDate.eq(reservationDate))
                .orderBy(ber.id.asc())
                .fetch();
    }

}
