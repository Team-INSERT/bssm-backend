package com.insert.ogbsm.presentation.meister;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.meister.dto.request.MeisterDetailRequest;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterDetailResponse;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResponse;
import com.insert.ogbsm.service.meister.MeisterAvg;
import com.insert.ogbsm.service.meister.MeisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/meister")
@RequiredArgsConstructor
public class MeisterController {

    private final MeisterService meisterService;

    @PostMapping("/detail")
    public MeisterAvg getDetail(@RequestBody MeisterDetailRequest dto) throws IOException {
        return meisterService.getDetail(SecurityUtil.getCurrentUserWithLogin(), dto);
    }

    @GetMapping
    public MeisterResAndAvgAndMax get() {
        return meisterService.get(SecurityUtil.getCurrentUserWithLogin());
    }

    @GetMapping("/update")
    public MeisterResponse updateAndGet() {
        return meisterService.updateAndGet(SecurityUtil.getCurrentUserWithLogin());
    }

}
