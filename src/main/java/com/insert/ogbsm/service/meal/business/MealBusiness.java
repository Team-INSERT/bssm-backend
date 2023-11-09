package com.insert.ogbsm.service.meal.business;

import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.repo.MealRepo;
import com.insert.ogbsm.presentation.meal.dto.RawMealItemDto;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import com.insert.ogbsm.service.meal.implement.MealImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealBusiness {

    private final MealImplement mealImplement;
    private final MealRepo mealRepo;

    public MealRes getMeal(LocalDate date) {
        return mealImplement.getMealList(date);
    }

    public void updateMonthMeal(YearMonth date) throws IOException {
        List<RawMealItemDto> getRawMeal = mealImplement.getRawMonthMealList(date);
        List<Meal> meals = mealImplement.refineRawMeal(getRawMeal);
        mealImplement.deleteBeforeMeal(date);
        mealImplement.appendAll(meals);
    }
}
