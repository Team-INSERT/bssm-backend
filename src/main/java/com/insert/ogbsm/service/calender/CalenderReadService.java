package com.insert.ogbsm.service.calender;

import com.insert.ogbsm.domain.calender.repo.CalenderRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadReq;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalenderReadService {
    private final CalenderRepo calenderRepo;

    public List<CalenderReadRes> get(CalenderReadReq calenderReadReq, Optional<User> user) {

        LocalDate start = LocalDate.of(calenderReadReq.year(), calenderReadReq.month(), 1);
        LocalDate end = LocalDate.of(calenderReadReq.year(), calenderReadReq.month(), start.lengthOfMonth());

        Map<LocalDate, List<CalenderRes>> calenderMap = new TreeMap<>(calenderRepo.findMonthCalender(
                calenderReadReq.year(),
                calenderReadReq.month(),
                user.map(User::getGrade).orElse(Short.MIN_VALUE),
                user.map(User::getClass_number).orElse(Short.MIN_VALUE)
        ));

        fillAllDays(start, end, calenderMap);

        return calenderMap.entrySet()
                .stream()
                .map(e -> new CalenderReadRes(e.getKey(), e.getValue()))
                .toList();
    }

    private void fillAllDays(LocalDate start, LocalDate end, Map<LocalDate, List<CalenderRes>> calenderMap) {
        for (LocalDate i = start; i.isBefore(end) || i.isEqual(end); i = i.plusDays(1)) {
            if (!calenderMap.containsKey(i)) {
                calenderMap.put(i, new ArrayList<>());
            }
        }
    }

}
