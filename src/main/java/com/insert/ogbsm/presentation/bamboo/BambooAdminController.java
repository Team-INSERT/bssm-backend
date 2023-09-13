package com.insert.ogbsm.presentation.bamboo;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.BambooRes;
import com.insert.ogbsm.service.bamboo.BambooAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bamboo/admin")
public class BambooAdminController {

    private final BambooAdminService bambooAdminService;

    @GetMapping()
    public List<BambooRes> findAllBamboo() {
        return bambooAdminService.findAllBamboo();
    }

    @PutMapping("/{id}")
    public AllowedBambooRes CreateNewBamboo(@PathVariable Long id) {
        return bambooAdminService.allowBamboo(id, SecurityUtil.getCurrentUserWithLogin().getId());
    }

    @DeleteMapping("/{id}")
    public Long deleteBamboo(@PathVariable Long id) {
        return bambooAdminService.deleteBamboo(id);
    }

}
