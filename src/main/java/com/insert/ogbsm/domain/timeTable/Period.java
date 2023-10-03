package com.insert.ogbsm.domain.timeTable;

import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.timeTable.dto.TimeTableValueRes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.time.DayOfWeek.*;

@AllArgsConstructor
@Getter
public enum Period {
    // 기숙사
    MORNING_DORM(new Time(0, 0), new Time(6, 30)),
    MORNING_DORM_FRIDAY(new Time(0, 0), new Time(7, 30)),
    PREPARING_DORM(new Time(6, 50), new Time(7, 30)),
    NIGHT_DORM(new Time(20, 40), new Time(24, 0)),

    // 아침운동
    MORNING_EXERSIZE(new Time(6, 30), new Time(6, 50)),

    //아침 자습시간
    MORNING_SELF_STUDY(new Time(8, 5), new Time(8, 30)),

    //시간표
    FIRST(new Time(8, 40), new Time(9, 30)),
    SECOND(new Time(9, 40), new Time(10, 30)),
    THIRD(new Time(10, 40), new Time(11, 30)),
    FOURTH(new Time(11, 40), new Time(12, 30)),
    FIFTH(new Time(1, 20), new Time(2, 10)),
    SIXTH(new Time(2, 20), new Time(3, 10)),
    SEVENTH(new Time(3, 20), new Time(4, 10)),
    AFTER_SCHOOL_1(new Time(4, 30), new Time(6, 10)),
    AFTER_SCHOOL_2(new Time(7, 0), new Time(8, 40)),

    //조례, 종례
    PRONUNCIATION(new Time(8, 30), new Time(8, 40)),
    ANTONYM(new Time(4, 20), new Time(4, 30)),
    CLEANING(new Time(4, 10), new Time(4, 20)),

    //쉬는 시간
    BREAK_1(new Time(9, 30), new Time(9, 40)),
    BREAK_2(new Time(10, 30), new Time(10, 40)),
    BREAK_3(new Time(11, 30), new Time(11, 40)),
    BREAK_4(new Time(2, 10), new Time(2, 20)),
    BREAK_5(new Time(3, 10), new Time(3, 20)),

    //식사
    BREAKFAST(new Time(7, 30), new Time(8, 5)),
    LUNCH(new Time(12, 30), new Time(1, 20)),
    DINNER(new Time(6, 10), new Time(7, 0)),

    //금요일
    ANTONYM_FRIDAY(new Time(3, 10), new Time(3, 20)),
    GO_HOME(new Time(3, 20), new Time(24, 0)),

    //토요일
    HOME(new Time(0, 0), new Time(24, 0)),

    //일요일
    HOME_SUNDAY(new Time(0, 0), new Time(7, 50)),
    DORM_SUNDAY(new Time(7, 50), new Time(24, 0));


    final Time startTime;
    final Time endTime;

    public static Period get(int period) {
        switch (period) {
            case 1 -> {
                return FIRST;
            }
            case 2 -> {
                return SECOND;
            }
            case 3 -> {
                return THIRD;
            }
            case 4 -> {
                return FOURTH;
            }
            case 5 -> {
                return FIFTH;
            }
            case 6 -> {
                return SIXTH;
            }
            case 7 -> {
                return SEVENTH;
            }
        }
        throw new BsmException(ErrorCode.NO_PERIOD_MATCHED);
    }

    public static Map<DayOfWeek, List<TimeTableValueRes>> getAllPeriod(Map<DayOfWeek, List<TimeTableValueRes>> periodMap) {
        setAtLeastSeventhAndBreak(periodMap);

        for (DayOfWeek day : periodMap.keySet()) {
            switch (day) {
                case MONDAY -> periodMap.replace(day, getMonday(periodMap.get(day)));
                case TUESDAY -> periodMap.replace(day, getTuesday(periodMap.get(day)));
                case WEDNESDAY -> periodMap.replace(day, getWednesday(periodMap.get(day)));
                case THURSDAY -> periodMap.replace(day, getThursday(periodMap.get(day)));
                case FRIDAY -> periodMap.replace(day, getFriday(periodMap.get(day)));
            }
        }
        periodMap.put(SATURDAY, getSaturday());
        periodMap.put(SUNDAY, getSunday());

        return periodMap;
    }

    private static void setAtLeastSeventhAndBreak(Map<DayOfWeek, List<TimeTableValueRes>> periodMap) {
        for (DayOfWeek day : periodMap.keySet()) {
            if (day != FRIDAY) {
                List<TimeTableValueRes> timeTableValueRes = periodMap.get(day);
                for (int i = periodMap.get(day).size(); i < 7; i++) {
                    timeTableValueRes.add(
                            new TimeTableValueRes(
                                    get(i + 1),
                                    "자습",
                                    null
                            )
                    );
                }
                periodMap.replace(day, setBreakAndLunch(timeTableValueRes));
            }
        }
    }

    public static Map<DayOfWeek, List<TimeTableValueRes>> setAtLeastSeventh(Map<DayOfWeek, List<TimeTableValueRes>> periodMap) {
        for (DayOfWeek day : periodMap.keySet()) {
            List<TimeTableValueRes> timeTableValueRes = periodMap.get(day);
            for (int i = periodMap.get(day).size(); i < 7; i++) {
                timeTableValueRes.add(
                        new TimeTableValueRes(
                                get(i + 1),
                                "자습",
                                null
                        )
                );
            }
        }

        return periodMap;
    }

    private static List<TimeTableValueRes> getMonday(List<TimeTableValueRes> timeTableValueRes) {
        List<TimeTableValueRes> monday = setOrdinaryMorning(timeTableValueRes);
        return setOrdinaryAfternoon(monday);

    }

    private static List<TimeTableValueRes> getTuesday(List<TimeTableValueRes> timeTableValueRes) {
        List<TimeTableValueRes> tuesday = setOrdinaryMorning(timeTableValueRes);
        return setOrdinaryAfternoon(tuesday);
    }

    private static List<TimeTableValueRes> getWednesday(List<TimeTableValueRes> timeTableValueRes) {
        List<TimeTableValueRes> wednesday = setOrdinaryMorning(timeTableValueRes);
        return setOrdinaryAfternoon(wednesday);
    }

    private static List<TimeTableValueRes> getThursday(List<TimeTableValueRes> timeTableValueRes) {
        List<TimeTableValueRes> thursday = setOrdinaryMorning(timeTableValueRes);
        return setOrdinaryAfternoon(thursday);
    }

    private static List<TimeTableValueRes> setBreakAndLunch(List<TimeTableValueRes> timeTableValueRes) {
        timeTableValueRes.add(1, new TimeTableValueRes(BREAK_1, "쉬는 시간", null));
        timeTableValueRes.add(3, new TimeTableValueRes(BREAK_2, "쉬는 시간", null));
        timeTableValueRes.add(5, new TimeTableValueRes(BREAK_3, "쉬는 시간", null));
        timeTableValueRes.add(7, new TimeTableValueRes(LUNCH, "점심 시간", null));
        timeTableValueRes.add(9, new TimeTableValueRes(BREAK_4, "쉬는 시간", null));
        timeTableValueRes.add(11, new TimeTableValueRes(BREAK_5, "쉬는 시간", null));

        return timeTableValueRes;
    }

    private static List<TimeTableValueRes> setOrdinaryMorning(List<TimeTableValueRes> day) {
        List<TimeTableValueRes> ordinary = new java.util.ArrayList<>(Stream.of(
                new TimeTableValueRes(MORNING_DORM, "기숙사", null),
                new TimeTableValueRes(MORNING_EXERSIZE, "아침 운동", null),
                new TimeTableValueRes(PREPARING_DORM, "등교 준비", null),
                new TimeTableValueRes(BREAKFAST, "아침 식사", null),
                new TimeTableValueRes(MORNING_SELF_STUDY, "아침 자습 시간", null),
                new TimeTableValueRes(PRONUNCIATION, "조례", null)
        ).toList());

        ordinary.addAll(day);

        return ordinary;
    }

    private static List<TimeTableValueRes> setOrdinaryAfternoon(List<TimeTableValueRes> day) {
        List<TimeTableValueRes> ordinary = Stream.of(
                new TimeTableValueRes(CLEANING, "청소 시간", null),
                new TimeTableValueRes(ANTONYM, "종례", null),
                new TimeTableValueRes(AFTER_SCHOOL_1, "방과후", null),
                new TimeTableValueRes(DINNER, "저녁 식사", null),
                new TimeTableValueRes(AFTER_SCHOOL_2, "방과후", null),
                new TimeTableValueRes(NIGHT_DORM, "기숙사", null)
        ).toList();

        day.addAll(ordinary);

        return day;
    }

    private static List<TimeTableValueRes> getFriday(List<TimeTableValueRes> timeTableValueRes) {
        List<TimeTableValueRes> friday = new java.util.ArrayList<>(Stream.of(
                new TimeTableValueRes(MORNING_DORM_FRIDAY, "기숙사", null),
                new TimeTableValueRes(BREAKFAST, "아침 식사", null),
                new TimeTableValueRes(MORNING_SELF_STUDY, "아침 자습 시간", null),
                new TimeTableValueRes(PRONUNCIATION, "조례", null)
        ).toList());

        friday.addAll(timeTableValueRes);

        friday.addAll(
                List.of(
                        new TimeTableValueRes(ANTONYM_FRIDAY, "종례", null),
                        new TimeTableValueRes(GO_HOME, "집", null)
                )
        );

        return friday;
    }

    private static List<TimeTableValueRes> getSaturday() {
        return List.of(
                new TimeTableValueRes(HOME, "집", null)
        );
    }

    private static List<TimeTableValueRes> getSunday() {
        return List.of(
                new TimeTableValueRes(HOME_SUNDAY, "집", null),
                new TimeTableValueRes(DORM_SUNDAY, "기숙사", null)
        );
    }

    public record Time(int hour, int minute) {

    }

}
