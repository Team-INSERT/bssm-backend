package com.insert.ogbsm.presentation.meal;

import com.insert.ogbsm.service.meal.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.YearMonth;

@RestController
@RequestMapping("admin/meal")
@RequiredArgsConstructor
public class MealAdminController {

    private final MealService mealService;

    @PutMapping("{date}")
    public void updateMonthMeal(@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth date) throws IOException {
        mealService.updateMonthMeal(date);
    }

}
