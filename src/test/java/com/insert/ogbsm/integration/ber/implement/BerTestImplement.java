package com.insert.ogbsm.integration.ber.implement;

import com.insert.ogbsm.common.IntegrationTest;
import com.insert.ogbsm.domain.ber.Ber;
import com.insert.ogbsm.domain.ber.repo.BerRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.ber.dto.BerReadRes;
import com.insert.ogbsm.presentation.ber.dto.BerRes;
import com.insert.ogbsm.service.ber.business.BerBusiness;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

public class BerTestImplement extends IntegrationTest {

    @Autowired
    private BerBusiness berBusiness;

    @Autowired
    private BerRepo berRepo;

    @Autowired
    private UserRepo userRepo;

    protected static Ber Ber_생성(LocalDate localDate, int berNumber, Long userId, String userName) {
        return Ber.builder()
                .reservationDate(localDate)
                .berNumber(berNumber)
                .reservationUserId(userId)
                .reservationUsersName(userName)
                .build();
    }

    protected void Ber_예약하기(Ber 예약할_Ber, LocalDate 현재시간) {
        berBusiness.berReserve(예약할_Ber, 현재시간);
        userRepo.save(
                User.builder()
                        .id(예약할_Ber.getReservationUserId())
                        .email(예약할_Ber.getReservationUsersName()+ ".com")
                        .nickname(예약할_Ber.getReservationUsersName())
                        .build()
        );

    }

    protected void Ber가_예약되었는지_검증(Ber 예약한_Ber) {
        Optional<Ber> 조회한_Ber = berRepo.findById(예약한_Ber.getId());

        if (조회한_Ber.isEmpty()) {
            fail("Ber가 예약되지 않았습니다.");
        }

        if (!예약한_Ber.equals(조회한_Ber.get())) {
            fail("조회된 Ber가 같지 않습니다.");
        }
    }

    protected void Ber_예약취소하기(Ber 예약취소할_Ber, Long 예약_취소하는_사용자_아이디) {
        berBusiness.berCancel(예약취소할_Ber.getId(), 예약_취소하는_사용자_아이디);
    }

    protected void Ber가_예약취소되었는지_검증(Ber 예약취소한_Ber) {
        Optional<Ber> 삭제한_Ber = berRepo.findById(예약취소한_Ber.getId());

        if (삭제한_Ber.isPresent()) {
            fail("Ber가 삭제되지 않았습니다.");
        }
    }

    protected void Ber_전체_예약하기(List<Ber> 저장할_베르실들, LocalDate 날짜) {
        for (Ber ber : 저장할_베르실들) {
            Ber_예약하기(ber, 날짜);
        }
    }

    protected BerReadRes 예약된_날짜로_Ber_조회(LocalDate localDate) {
        return berBusiness.getBer(localDate);
    }

    protected void 조회된_Bers가_같은지_검증(BerReadRes 조회된_Bers, List<Ber> 저장할_Ber, LocalDate 조회_날짜) {
        List<Ber> 조회돼야_하는_Ber = 저장할_Ber
                .stream()
                .filter(ber -> ber.getReservationDate().equals(조회_날짜))
                .toList();

        if (조회돼야_하는_Ber.size() == 조회된_Bers.getBerResList().size()) {
            for (Ber ber : 조회돼야_하는_Ber) {
                BerRes berRes = new BerRes(ber, User.builder().id(ber.getId()).build());
                if (조회된_Bers.getBerResList().contains(berRes)) {
                    continue;
                }
                fail("조회된 Ber와 조회돼야 하는 Ber가 다릅니다.");
            }
            return;
        }
        fail("조회된 개수가 다릅니다.");
    }


    public static class fixtures {
        public static Ber 베르실1_11월_14일_화_1L = Ber_생성(LocalDate.of(2023, 11, 14),
                1,
                1L,
                "이창보"
        );

        public static Ber 베르실2_11월_14일_화_2L = Ber_생성(LocalDate.of(2023, 11, 14),
                2,
                2L,
                "김호현"
        );

        public static Ber 베르실3_11월_14일_화_3L = Ber_생성(LocalDate.of(2023, 11, 14),
                3,
                3L,
                "박우빈"
        );

        public static Ber 베르실4_11월_15일_화_4L = Ber_생성(LocalDate.of(2023, 11, 15),
                4,
                4L,
                "권세원"
        );

        public static Ber 베르실3_11월_14일_화_1L = Ber_생성(LocalDate.of(2023, 11, 14),
                3,
                1L,
                "이창보"
        );

        public static Ber 베르실1_11월_14일_화_2L = Ber_생성(LocalDate.of(2023, 11, 14),
                1,
                2L,
                "김호현");

        public static Ber 베르실1_11월_14일_금_1L = Ber_생성(LocalDate.of(2023, 11, 17),
                1,
                1L,
                "이창보");
    }
}
