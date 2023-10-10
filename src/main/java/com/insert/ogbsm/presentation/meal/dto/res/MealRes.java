package com.insert.ogbsm.presentation.meal.dto.res;

import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.MealType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MealRes {

    private final Map<MealType, MealResItem> data = new HashMap<>();
    private List<MealType> keys;

    public static MealRes create(List<Meal> mealList) {
        MealRes res = new MealRes();
        mealList.forEach(meal ->
                res.data.put(meal.getType(), MealResItem.create(meal))
        );
        res.keys = mealList.stream()
                .map(Meal::getType)
                .collect(Collectors.toList());

        // Enum을 MORNING, LUNCH, DINNER 순으로 정렬하는 Comparator 생성
        Comparator<MealType> mealTypeComparator = Comparator.comparingInt(Enum::ordinal);

        // 정렬
        res.keys.sort(mealTypeComparator);
        return res;
    }
}
