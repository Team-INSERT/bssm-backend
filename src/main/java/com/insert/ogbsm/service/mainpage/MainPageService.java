package com.insert.ogbsm.service.mainpage;

import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderSimpleRes;
import com.insert.ogbsm.presentation.mainpage.dto.MainRes;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.bamboo.BambooService;
import com.insert.ogbsm.service.calender.CalenderReadService;
import com.insert.ogbsm.service.meal.MealService;
import com.insert.ogbsm.service.meister.MeisterRankingService;
import com.insert.ogbsm.service.meister.MeisterService;
import com.insert.ogbsm.service.post.PostReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainPageService {
    private final MealService mealService;
    private final CalenderReadService calenderReadService;
    private final BambooService bambooService;
    private final MeisterService meisterService;
    private final PostReadService postReadService;
    private final MeisterRankingService meisterRankingService;


    public MainRes get(LocalDate now, User currentUser) {
        MealRes meal = mealService.getMeal(now);

        CalenderSimpleRes calender = null;
        MeisterResAndAvgAndMax meisterResAndAvgAndMax1 = null;
        Integer ranking = null;
        AllowedBambooRes allowedBambooRes = null;
        List<PostRes> common = null;
        List<PostRes> notice = null;

        if (currentUser != null) {
            calender = calenderReadService.getOne(now);
            ranking = meisterRankingService.getRankingOne(currentUser);
            meisterResAndAvgAndMax1 = meisterService.get(currentUser);
            allowedBambooRes = bambooService.findMostRecentAllowedBamboo();
            common = postReadService.readTop5ByCategory(Category.COMMON);
            notice = postReadService.readTop5ByCategory(Category.NOTICE);
        }

        return new MainRes(
                meal,
                calender,
                meisterResAndAvgAndMax1,
                ranking,
                allowedBambooRes,
                common,
                notice
        );
    }
}
