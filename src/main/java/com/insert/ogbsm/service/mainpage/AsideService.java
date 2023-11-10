package com.insert.ogbsm.service.mainpage;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.calender.dto.CalenderSimpleRes;
import com.insert.ogbsm.presentation.mainpage.dto.dto.AsideRes;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.service.calender.business.CalenderBusiness;
import com.insert.ogbsm.service.checkIn.business.CheckInBusiness;
import com.insert.ogbsm.service.meal.business.MealBusiness;
import com.insert.ogbsm.service.meister.business.MeisterRankingBusiness;
import com.insert.ogbsm.service.meister.business.MeisterBusiness;
import com.insert.ogbsm.service.room.RoomRead;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsideService {
    private final MealBusiness mealBusiness;
    private final CalenderBusiness calenderBusiness;
    private final MeisterBusiness meisterBusiness;
    private final MeisterRankingBusiness meisterRankingBusiness;
    private final CheckInBusiness checkInBusiness;
    private final RoomRead roomRead;

    public AsideRes get(LocalDate now, User currentUser) {
        MealRes meal = mealBusiness.getMeal(now);
        CalenderSimpleRes calender = null;
        MeisterResAndAvgAndMax meisterResAndAvgAndMax1 = null;
        Integer ranking = null;
        Room room = null;
        Boolean checkInRes = null;

        if (currentUser != null) {
            calender = calenderBusiness.readByDate(now);
            ranking = meisterRankingBusiness.getRankingOne(currentUser);
            meisterResAndAvgAndMax1 = meisterBusiness.get(currentUser);
            checkInRes = checkInBusiness.readMyCheckIn(currentUser.getId());
            room = roomRead.getMyRoom(currentUser.getId());

        }

        return new AsideRes(
                meal,
                calender,
                meisterResAndAvgAndMax1,
                ranking,
                room,
                checkInRes
        );
    }
}
