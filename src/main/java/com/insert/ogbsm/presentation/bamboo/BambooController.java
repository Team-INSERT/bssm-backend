package com.insert.ogbsm.presentation.bamboo;

import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.CreateBambooReq;
import com.insert.ogbsm.service.bamboo.BambooService;
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

    private final BambooService bambooService;

    @GetMapping()
    public Slice<AllowedBambooRes> findAllAllowedBamboo(Pageable pageable) {
        return bambooService.findAllAllowedBamboo(pageable);
    }

    @PostMapping()
    public Long CreateNewBamboo(@RequestBody @Valid CreateBambooReq createBambooReq) {
        return bambooService.createBamboo(createBambooReq);
    }

}
