package com.insert.ogbsm.presentation.mainpage.dto.dto;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.presentation.calender.dto.CalenderSimpleRes;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;

public record AsideRes(MealRes meal,
                       CalenderSimpleRes calender,
                       MeisterResAndAvgAndMax meister,
                       Integer ranking,
                       Room room,
                       Boolean isCheckIn
                    ){
}
