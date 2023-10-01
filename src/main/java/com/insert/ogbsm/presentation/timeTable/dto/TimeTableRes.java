package com.insert.ogbsm.presentation.timeTable.dto;

import com.insert.ogbsm.domain.timeTable.Period;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class TimeTableRes {
    private final Map<DayOfWeek, List<TimeTableValueRes>> timeTable;

    public TimeTableRes(List<TimeTableValueRes> timeTableResList) {
        Map<DayOfWeek, List<TimeTableValueRes>> groupedByDayOfWeek = new HashMap<>();

        // timeTableResList를 요일에 따라 그룹화
        for (TimeTableValueRes entry : timeTableResList) {
            DayOfWeek dayOfWeek = entry.getDate().getDayOfWeek();
            groupedByDayOfWeek.computeIfAbsent(dayOfWeek, k -> new ArrayList<>()).add(entry);
        }

        timeTable = Period.getAllPeriod(groupedByDayOfWeek);
    }

    public TimeTableRes(List<TimeTableValueRes> timeTableResList, String table) {
        Map<DayOfWeek, List<TimeTableValueRes>> groupedByDayOfWeek = new HashMap<>();

        // timeTableResList를 요일에 따라 그룹화
        for (TimeTableValueRes entry : timeTableResList) {
            DayOfWeek dayOfWeek = entry.getDate().getDayOfWeek();
            groupedByDayOfWeek.computeIfAbsent(dayOfWeek, k -> new ArrayList<>()).add(entry);
        }

        this.timeTable = Period.setAtLeastSeventh(groupedByDayOfWeek);
    }
}
