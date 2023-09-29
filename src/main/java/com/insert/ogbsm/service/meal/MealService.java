package com.insert.ogbsm.service.meal;


import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.repo.MealRepo;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealFacade mealFacade;
    private final MealProvider mealProvider;
    private final MealRepo mealRepo;

    public MealRes getMeal(LocalDate date) {
         List<Meal> mealList = mealFacade.getMealList(date);
         return MealRes.create(mealList);
    }

    public void updateMonthMeal(YearMonth date) throws IOException {
        List<Meal> mealList = mealProvider.getRawMonthMealList(date).stream()
                .map(meal -> meal.toEntity(mealFacade.filterMealStr(meal.DDISH_NM())))
                .toList();

        List<Meal> deleteList = mealRepo.findAllByPkDateBetween(date.atDay(1), date.atEndOfMonth());
        mealList.forEach(deleteList::remove);
        mealRepo.deleteAll(deleteList);

        mealRepo.saveAll(mealList);
    }

}
