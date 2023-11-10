package com.insert.ogbsm.presentation.meister;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.meister.dto.request.UpdateMeisterPrivateRequest;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterRankingResponse;
import com.insert.ogbsm.service.meister.business.MeisterRankingBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meister")
@RequiredArgsConstructor
public class MeisterRankingController {

    private final MeisterRankingBusiness meisterRankingBusiness;

    @GetMapping("/ranking/{grade}")
    public List<MeisterRankingResponse> getRanking(@PathVariable int grade) {
        return meisterRankingBusiness.getRanking(SecurityUtil.getCurrentUserWithLogin(), grade);
    }

    @PutMapping("/privateRanking")
    public void updatePrivateRanking(@RequestBody UpdateMeisterPrivateRequest dto) {
        meisterRankingBusiness.updatePrivateRanking(SecurityUtil.getCurrentUserWithLogin(), dto.isPrivateRanking());
    }
}
