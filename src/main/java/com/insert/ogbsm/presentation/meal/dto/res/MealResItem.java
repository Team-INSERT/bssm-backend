package com.insert.ogbsm.presentation.meal.dto.res;

import com.insert.ogbsm.domain.meal.Meal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MealResItem {

    private String content;
    private float cal;

    public static MealResItem create(Meal meal) {
        MealResItem resItem = new MealResItem();
        resItem.content = meal.getContent();
        resItem.cal = meal.getCal();
        return resItem;
    }
}
