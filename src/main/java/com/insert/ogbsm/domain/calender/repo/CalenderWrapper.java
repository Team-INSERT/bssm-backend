package com.insert.ogbsm.domain.calender.repo;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CalenderWrapper {
    private final CalenderRepo calenderRepo;

    public Calender findById(Long id) {
        return calenderRepo.findById(id)
                .orElseThrow(() -> new BsmException(ErrorCode.CALENDER_NOT_FOUND));
    }

}
