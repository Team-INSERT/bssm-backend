package com.insert.ogbsm.service.calender;

import com.insert.ogbsm.domain.calender.repo.CalenderRepo;
import com.insert.ogbsm.presentation.calender.dto.CalenderGraphRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalenderReadService {
    private final CalenderRepo calenderRepo;

    public CalenderGraphRes get(CalenderReadReq calenderReadReq) {
        return calenderRepo.get(calenderReadReq.year(), calenderReadReq.month(), calenderReadReq.grade(), calenderReadReq.classNumber());
    }
}
