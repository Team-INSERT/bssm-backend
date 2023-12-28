package com.insert.ogbsm.service.meal.business;

import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.repo.MealRepo;
import com.insert.ogbsm.service.meal.implement.MealImplement;
import com.insert.ogbsm.service.meal.implement.MealProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.YearMonth;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MealScheduler {

    private final MealRepo mealRepo;
    private final MealImplement mealImplement;
    private final MealProvider mealProvider;

    @Scheduled(cron = "0 0 1 * * ?")
    private void getMonthMeal() throws IOException {
        YearMonth nextMonth = YearMonth.now();
        List<Meal> mealList = mealProvider.getRawMonthMealList(nextMonth).stream()
                .map(meal -> meal.toEntity(mealImplement.filterMealStr(meal.DDISH_NM())))
                .toList();

        mealRepo.saveAll(mealList);
    }
}
