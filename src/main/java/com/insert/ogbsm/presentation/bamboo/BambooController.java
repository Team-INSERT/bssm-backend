package com.insert.ogbsm.presentation.bamboo;

import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.CreateBambooReq;
import com.insert.ogbsm.service.bamboo.BambooService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bamboo")
public class BambooController {

    private final BambooService bambooService;

    @GetMapping()
    public List<AllowedBambooRes> findAllAllowedBamboo() {
        return bambooService.findAllAllowedBamboo();
    }

    @PostMapping()
    public Long CreateNewBamboo(@RequestBody @Valid CreateBambooReq createBambooReq) {
        return bambooService.createBamboo(createBambooReq);
    }

}
