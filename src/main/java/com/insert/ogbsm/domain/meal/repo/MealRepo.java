package com.insert.ogbsm.domain.meal.repo;

import com.insert.ogbsm.domain.meal.Meal;
import com.insert.ogbsm.domain.meal.MealPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepo extends JpaRepository<Meal, MealPk> {

    List<Meal> findByPkDate(LocalDate date);

    List<Meal> findAllByPkDateBetween(LocalDate start, LocalDate end);

}
