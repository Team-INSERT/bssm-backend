package com.insert.ogbsm.service.bamboo;

import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.domain.bamboo.repo.AllowedBambooRepo;
import com.insert.ogbsm.domain.bamboo.repo.BambooRepo;
import com.insert.ogbsm.global.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.CreateBambooDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BambooService {

    private final BambooRepo bambooRepo;
    private final AllowedBambooRepo allowedBambooRepo;

    @Transactional(readOnly = true)
    public List<AllowedBambooRes> findAllAllowedBamboo() {
        return allowedBambooRepo.findAll()
                .stream().map(AllowedBambooRes::new)
                .toList();
    }

    public Long createBamboo(CreateBambooDto createBambooDto) {
        Bamboo bamboo = bambooRepo.save(
                Bamboo.builder()
                        .content(createBambooDto.getContent())
                        .userId(SecurityUtil.getCurrentUserOrNotLogin().getId())
                        .isAllow(false)
                        .build()
        );
        return bamboo.getId();
    }

}
