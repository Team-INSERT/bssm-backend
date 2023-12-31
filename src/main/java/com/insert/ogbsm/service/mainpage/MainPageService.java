package com.insert.ogbsm.service.mainpage;

import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderSimpleRes;
import com.insert.ogbsm.presentation.mainpage.dto.MainRes;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.bamboo.business.BambooBusiness;
import com.insert.ogbsm.service.calender.business.CalenderBusiness;
import com.insert.ogbsm.service.meal.business.MealBusiness;
import com.insert.ogbsm.service.meister.business.MeisterBusiness;
import com.insert.ogbsm.service.meister.business.MeisterRankingBusiness;
import com.insert.ogbsm.service.post.business.PostBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainPageService {
    private final MealBusiness mealBusiness;
    private final CalenderBusiness calenderBusiness;
    private final BambooBusiness bambooBusiness;
    private final MeisterBusiness meisterBusiness;
    private final MeisterRankingBusiness meisterRankingBusiness;
    private final PostBusiness postBusiness;


    public MainRes get(LocalDate now, User currentUser) {
        MealRes meal = mealBusiness.getMeal(now);

        CalenderSimpleRes calender = null;
        MeisterResAndAvgAndMax meisterResAndAvgAndMax1 = null;
        Integer ranking = null;
        AllowedBambooRes allowedBambooRes = null;
        List<PostRes> common = null;
        List<PostRes> notice = null;

        if (currentUser != null) {
            calender = calenderBusiness.readByDate(now);
            ranking = meisterRankingBusiness.getRankingOne(currentUser);
            meisterResAndAvgAndMax1 = meisterBusiness.get(currentUser);
            allowedBambooRes = new AllowedBambooRes(bambooBusiness.findMostRecentAllowedBamboo());
            common = postBusiness.readTop5ByCategory(Category.COMMON);
            notice = postBusiness.readTop5ByCategory(Category.NOTICE);
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
