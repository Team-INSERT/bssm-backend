package com.insert.ogbsm.service.meal;

import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.repo.MealRepo;
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
    private final MealFacade mealFacade;
    private final MealProvider mealProvider;

    @Scheduled(cron = "0 0 0 28 * ?")
    private void getMonthMeal() throws IOException {
        YearMonth nextMonth = YearMonth.now().plusMonths(1);
        List<Meal> mealList = mealProvider.getRawMonthMealList(nextMonth).stream()
                .map(meal -> meal.toEntity(mealFacade.filterMealStr(meal.getDDISH_NM())))
                .toList();

        mealRepo.saveAll(mealList);
    }
}
