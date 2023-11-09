package com.insert.ogbsm.presentation.mainpage.dto.dto;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.presentation.calender.dto.CalenderSimpleRes;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.presentation.user.dto.UserResponse;

public record AsideRes(UserResponse userResponse,
                       Integer ranking ,
                       MeisterResAndAvgAndMax meisterResAndAvgAndMax1 ,
                       Boolean checkInRes ,
                       Room room
){ }
