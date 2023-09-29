package com.insert.ogbsm.presentation.meal.dto;

import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.MealType;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RawMealItemDto {
    private final String DDISH_NM;
    private final String MLSV_YMD;
    private final String MMEAL_SC_NM;
    private final String CAL_INFO;

    public RawMealItemDto(String DDISH_NM, String MLSV_YMD, String MMEAL_SC_NM, String CAL_INFO) {
        this.DDISH_NM = DDISH_NM;
        this.MLSV_YMD = MLSV_YMD;
        this.MMEAL_SC_NM = MMEAL_SC_NM;
        this.CAL_INFO = CAL_INFO;
    }

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

    @Override
    public String toString() {
        return "RawMealItemDto[" +
                "DDISH_NM=" + DDISH_NM + ", " +
                "MLSV_YMD=" + MLSV_YMD + ", " +
                "MMEAL_SC_NM=" + MMEAL_SC_NM + ", " +
                "CAL_INFO=" + CAL_INFO + ']';
    }

    public String DDISH_NM() {
        return DDISH_NM;
    }
}
