package com.insert.ogbsm.presentation.meal.dto;

import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.MealType;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @param DDISH_NM    급식 식단 정보 문자열
 * @param MLSV_YMD    급식 날짜
 * @param MMEAL_SC_NM 급식 시간
 * @param CAL_INFO    칼로리 정보
 */
public record RawMealItemDto(String DDISH_NM, String MLSV_YMD, String MMEAL_SC_NM, String CAL_INFO) {
    public Meal toEntity(String content) {
        LocalDate date = LocalDate.parse(MLSV_YMD, DateTimeFormatter.ofPattern("yyyyMMdd"));
        MealType type = MealType.create(MMEAL_SC_NM);
        float cal = parseCalInfo();
        return Meal.create(date, type, content, cal);
    }

    private float parseCalInfo() {
        Matcher calMatch = Pattern.compile("[0-9.]+").matcher(CAL_INFO);
        if (calMatch.find()) return Float.parseFloat(calMatch.group());

        throw new BsmException(ErrorCode.MEAL_TYPE_PARSE);
    }
}
