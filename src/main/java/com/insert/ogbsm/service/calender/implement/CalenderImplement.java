package com.insert.ogbsm.service.calender.implement;

import com.insert.ogbsm.domain.calender.Calender;
import com.insert.ogbsm.domain.calender.repo.CalenderRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderSimpleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CalenderImplement {
    private final CalenderRepo calenderRepo;

    public Calender append(Calender calender) {
        return calenderRepo.save(calender);
    }

    public Calender read(Long calenderId) {
        return calenderRepo.findById(calenderId)
                .orElseThrow(() -> new BsmException(ErrorCode.CALENDER_NOT_FOUND));

    }

    public void remove(Calender calender) {
        calenderRepo.delete(calender);
    }

    public StartAndEndDate getStartAndEndDay(int year, int month) {
        LocalDate start =
                LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, start.lengthOfMonth());

        return new StartAndEndDate(start, end);
    }

    public List<CalenderReadRes> readAll(int year, int month, Optional<User> user, StartAndEndDate startAndEndDate) {
        Map<LocalDate, List<CalenderRes>> calenderMap = new TreeMap<>(calenderRepo.findMonthCalender(
                year,
                month,
                user.map(User::getGrade).orElse(Short.MIN_VALUE),
                user.map(User::getClass_number).orElse(Short.MIN_VALUE)
        ));

        fillAllDays(startAndEndDate.start(), startAndEndDate.end(), calenderMap);

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

    public CalenderSimpleRes readByDate(LocalDate date) {
        return new CalenderSimpleRes(calenderRepo.findByDate(date));
    }

    public record StartAndEndDate(
            LocalDate start,
            LocalDate end
    ) {}
}
