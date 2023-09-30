package com.insert.ogbsm.presentation.bamboo;

import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.BambooRes;
import com.insert.ogbsm.service.bamboo.BambooAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return bambooAdminService.allowBamboo(id);
    }

    @DeleteMapping("/{id}")
    public Long deleteBamboo(@PathVariable Long id) {
        return bambooAdminService.deleteBamboo(id);
    }

}
