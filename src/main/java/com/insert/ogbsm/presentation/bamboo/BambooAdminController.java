package com.insert.ogbsm.presentation.bamboo;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.BambooRes;
import com.insert.ogbsm.service.bamboo.business.BambooAdminBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bamboo/admin")
public class BambooAdminController {

    private final BambooAdminBusiness bambooAdminBusiness;

    @GetMapping()
    public List<BambooRes> findAllBamboo() {
        return bambooAdminBusiness.findAllBamboo()
                .stream()
                .map(BambooRes::new)
                .toList();
    }

    @PutMapping("/{id}")
    public AllowedBambooRes CreateNewBamboo(@PathVariable Long id) {
        AllowedBamboo allowedBamboo = bambooAdminBusiness.allowBamboo(id, SecurityUtil.getCurrentUserWithLogin());
        return new AllowedBambooRes(allowedBamboo);
    }

    @DeleteMapping("/{id}")
    public Long deleteBamboo(@PathVariable Long id) {
        return bambooAdminBusiness.deleteBamboo(id);
    }

}
