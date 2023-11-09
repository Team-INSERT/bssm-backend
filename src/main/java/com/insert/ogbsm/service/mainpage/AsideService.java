package com.insert.ogbsm.service.mainpage;

import com.insert.ogbsm.domain.room.Room;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.mainpage.dto.dto.AsideRes;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.presentation.user.dto.UserResponse;
import com.insert.ogbsm.service.calender.business.CalenderBusiness;
import com.insert.ogbsm.service.checkIn.business.CheckInBusiness;
import com.insert.ogbsm.service.meal.MealService;
import com.insert.ogbsm.service.meister.MeisterRankingService;
import com.insert.ogbsm.service.meister.MeisterService;
import com.insert.ogbsm.service.room.RoomRead;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsideService {
    private final MeisterService meisterService;
    private final MeisterRankingService meisterRankingService;
    private final CheckInBusiness checkInBusiness;
    private final RoomRead roomRead;

    public AsideRes get(LocalDate now, User currentUser) {
        UserResponse userResponse = new UserResponse(currentUser);
        Integer ranking = meisterRankingService.getRankingOne(currentUser);
        MeisterResAndAvgAndMax meisterResAndAvgAndMax1 = meisterService.get(currentUser);
        Boolean checkInRes = checkInBusiness.readMyCheckIn(currentUser.getId());
        Room room = null;

        try {
           room = roomRead.getMyRoom(currentUser.getId());
        } catch (Exception e){}


        return new AsideRes(
                userResponse,
                ranking,
                meisterResAndAvgAndMax1,
                checkInRes,
                room
        );
    }
}
