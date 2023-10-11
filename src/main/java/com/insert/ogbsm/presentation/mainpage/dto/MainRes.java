package com.insert.ogbsm.presentation.mainpage.dto;

import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.presentation.post.dto.PostRes;

import java.util.List;

public record MainRes(MealRes meal, com.insert.ogbsm.presentation.calender.dto.CalenderSimpleRes calender,
                      MeisterResAndAvgAndMax meister,
                      Integer ranking,
                      AllowedBambooRes bamboo,
                      List<PostRes> common,
                      List<PostRes> notice) {
}
