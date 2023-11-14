package com.insert.ogbsm.integration.ber.business;

import com.insert.ogbsm.domain.ber.Ber;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.integration.ber.implement.BerTestImplement;
import com.insert.ogbsm.presentation.ber.dto.BerReadRes;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.insert.ogbsm.integration.ber.implement.BerTestImplement.fixtures.*;

public class BerBusinessTest extends BerTestImplement {

    @Test
    @Transactional(readOnly = true)
    void 예약된_날짜로_베르실을_전체_조회한다() {
        //given
        List<Ber> 저장할_Ber = List.of(베르실1_11월_14일_화_1L,
                베르실2_11월_14일_화_2L,
                베르실3_11월_14일_화_3L,
                베르실4_11월_15일_화_4L
                );

        Ber_전체_예약하기(저장할_Ber, LocalDate.of(2023, 11, 12));
        LocalDate 조회_날짜 = LocalDate.of(2023, 11, 14);
        //when
        BerReadRes 조회된_Bers = 예약된_날짜로_Ber_조회(조회_날짜);
        //then
        조회된_Bers가_같은지_검증(조회된_Bers, 저장할_Ber, 조회_날짜);
    }

    @Nested
    class Ber를_예약한다 {

        @Test
        void 베르실을_예약한다() {
            //when
            Ber_예약하기(베르실1_11월_14일_화_1L, LocalDate.of(2023, 11, 12));
            //then
            Ber가_예약되었는지_검증(베르실1_11월_14일_화_1L);
        }

        @Test
        void 한_사람이_같은_날에_2개를_예약할_때_Ber_User_Already_Reserved_Same_Time가_발생한다() {
            //given
            Ber_예약하기(베르실1_11월_14일_화_1L, LocalDate.of(2023, 11, 12));

            //when, then
            exceptionHandler
                    .함수를_실행할_시_다음의_에러코드를_반환한다(
                            () -> Ber_예약하기(베르실3_11월_14일_화_1L, LocalDate.of(2023, 11, 12)),
                            ErrorCode.Ber_User_Already_Reserved_Same_Time
                    );
        }

        @Test
        void 하나의_Ber가_같은_날에_2번_예약되려고_한다면_Ber_Already_Reserved가_발생한다() {
            //given
            Ber_예약하기(베르실1_11월_14일_화_1L, LocalDate.of(2023, 11, 12));

            //when, then
            exceptionHandler
                    .함수를_실행할_시_다음의_에러코드를_반환한다(
                            () -> Ber_예약하기(베르실1_11월_14일_화_2L, LocalDate.of(2023, 11, 12)),
                            ErrorCode.Ber_Already_Reserved
                    );
        }

        @Test
        void 일요일부터_목요일_사이가_아니면_Ber_Reservation_Time_Not_Monday_To_Sunday가_발생한다() {
            //when, then
            exceptionHandler
                    .함수를_실행할_시_다음의_에러코드를_반환한다(
                            () -> Ber_예약하기(베르실1_11월_14일_금_1L, LocalDate.of(2023, 11, 12)),
                            ErrorCode.Ber_Reservation_Time_Not_Monday_To_Sunday
                    );
        }

        @Test
        void 예약하려는_날짜가_지금보다_전이면_Ber_Reservation_Time_Before_Now가_발생한다() {
            //when, then
            exceptionHandler
                    .함수를_실행할_시_다음의_에러코드를_반환한다(
                            () -> Ber_예약하기(베르실1_11월_14일_화_1L, LocalDate.of(2023, 11, 16)),
                            ErrorCode.Ber_Reservation_Time_Before_Now
                    );
        }
    }

    @Nested
    class Ber를_예약취소한다 {
        @Test
        void 베르실을_예약취소한다() {
            //given
            Ber_예약하기(베르실1_11월_14일_화_1L, LocalDate.of(2023, 11, 12));
            //when
            Ber_예약취소하기(베르실1_11월_14일_화_1L, 베르실1_11월_14일_화_1L.getReservationUserId());
            //then
            Ber가_예약취소되었는지_검증(베르실1_11월_14일_화_1L);
        }

        @Test
        void 다른_유저가_베르실을_취소하고자_한다면_NOT_SAME_USER가_발생한다() {
            //given
            Ber_예약하기(베르실1_11월_14일_화_1L, LocalDate.of(2023, 11, 12));
            //when, then
            exceptionHandler
                    .함수를_실행할_시_다음의_에러코드를_반환한다(
                            () -> Ber_예약취소하기(베르실1_11월_14일_화_1L, 2L),
                            ErrorCode.NOT_SAME_USER
                    );
        }
    }
}
