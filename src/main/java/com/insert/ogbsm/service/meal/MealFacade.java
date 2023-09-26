package com.insert.ogbsm.service.meal;

import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.MealPk;
import com.insert.ogbsm.domain.meal.MealType;
import com.insert.ogbsm.domain.meal.repo.MealRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MealFacade {

    private final MealRepo mealRepo;

    public String filterMealStr(String str) {
        return str.replaceAll("<br/>|\\([0-9.]*?\\)|\\(산고\\)|\\(소마[0-9.]*?\\)", "").trim();
    }

    public String getTodayMealStr(MealType type) {
        return getMeal(LocalDate.now(), type).getContent();
    }

    public List<Meal> getMealList(LocalDate date) {
        return mealRepo.findByPkDate(date);
    }

    public Meal getMeal(LocalDate date, MealType type) {
        return mealRepo.findById(MealPk.create(date, type))
                .orElseThrow(() -> new BsmException(ErrorCode.MEAL_NOT_FOUND));
    }

}
