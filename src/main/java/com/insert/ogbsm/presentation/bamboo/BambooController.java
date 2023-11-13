package com.insert.ogbsm.presentation.bamboo;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.CreateBambooReq;
import com.insert.ogbsm.service.bamboo.business.BambooBusiness;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bamboo")
public class BambooController {

    private final BambooBusiness bambooBusiness;

    @GetMapping
    public Slice<AllowedBambooRes> findAllAllowedBamboo(Pageable pageable) {
        return bambooBusiness.findAllAllowedBamboo(pageable)
                .map(AllowedBambooRes::new);
    }

    @PostMapping
    public Long CreateNewBamboo(@RequestBody @Valid CreateBambooReq createBambooReq) {
        return bambooBusiness.createBamboo(createBambooReq.of(SecurityUtil.getCurrentUserIdWithLogin()));
    }

}
