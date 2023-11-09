package com.insert.ogbsm.service.meal.implement;

import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.MealPk;
import com.insert.ogbsm.domain.meal.MealType;
import com.insert.ogbsm.domain.meal.repo.MealRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.meal.dto.RawMealItemDto;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealImplement {

    private final MealRepo mealRepo;
    private final MealProvider mealProvider;

    public String filterMealStr(String str) {
        return str.replaceAll("\\([0-9.]*?\\)|\\(산고\\)|\\(소마[0-9.]*?\\)", "").trim();
    }

    public MealRes getMealList(LocalDate date) {
        return MealRes.create(mealRepo.findByPkDate(date));
    }

    public Meal getMeal(LocalDate date, MealType type) {
        return mealRepo.findById(MealPk.create(date, type))
                .orElseThrow(() -> new BsmException(ErrorCode.MEAL_NOT_FOUND));
    }

    public List<RawMealItemDto> getRawMonthMealList(YearMonth date) throws IOException {
        return mealProvider.getRawMonthMealList(date);
    }

    public List<Meal> refineRawMeal(List<RawMealItemDto> getRawMeal) {
        return getRawMeal.stream()
                .map(meal -> meal.toEntity(filterMealStr(meal.DDISH_NM())))
                .toList();
    }

    public void deleteBeforeMeal(YearMonth date) {
        List<Meal> beforeMeals = mealRepo.findAllByPkDateBetween(date.atDay(1), date.atEndOfMonth());
        beforeMeals.forEach(beforeMeals::remove);
    }

    public void appendAll(List<Meal> meals) {
        mealRepo.saveAll(meals);
    }
}
