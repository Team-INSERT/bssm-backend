package com.insert.ogbsm.service.ber;

import com.insert.ogbsm.domain.ber.repo.BerRepo;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.ber.dto.BerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BerRead {

    private final BerRepo berRepo;

    private final UserRepo userRepo;

    public List<BerRes> getBer(LocalDate reservation) {
        return berRepo.findByReservationDate(reservation).stream()
                .map(BerRes::new)
                .collect(Collectors.toList());
    }


    //TODO 유저 연결 생각


}
