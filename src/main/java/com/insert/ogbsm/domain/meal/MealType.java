package com.insert.ogbsm.domain.meal;


import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;

public enum MealType {
    MORNING,
    LUNCH,
    DINNER;

    public static MealType create(String type) {
        return switch (type) {
            case "조식" -> MealType.MORNING;
            case "중식" -> MealType.LUNCH;
            case "석식" -> MealType.DINNER;
            default -> throw new BsmException(ErrorCode.MEAL_TYPE_PARSE);
        };
    }
}
