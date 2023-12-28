package com.insert.ogbsm.presentation.meal;

import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import com.insert.ogbsm.service.meal.business.MealBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealBusiness mealBusiness;

    @GetMapping("/{date}")
    public MealRes getMeal(@PathVariable @DateTimeFormat(pattern = "yyMMdd") LocalDate date) {
        return mealBusiness.getMeal(date);
    }
}
