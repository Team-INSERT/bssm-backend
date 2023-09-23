package com.insert.ogbsm.service.bamboo;

import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.domain.bamboo.repo.AllowedBambooRepo;
import com.insert.ogbsm.domain.bamboo.repo.BambooRepo;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.CreateBambooReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BambooService {

    private final BambooRepo bambooRepo;
    private final AllowedBambooRepo allowedBambooRepo;

    @Transactional(readOnly = true)
    public Slice<AllowedBambooRes> findAllAllowedBamboo(Pageable pageable) {
        return allowedBambooRepo.findAll(pageable)
                .map(AllowedBambooRes::new);
    }

    public Long createBamboo(CreateBambooReq createBambooReq) {
        Bamboo bamboo = bambooRepo.save(
                Bamboo.builder()
                        .content(createBambooReq.getContent())
                        .userId(SecurityUtil.getCurrentUserWithLogin().getId())
                        .isAllow(false)
                        .build()
        );
        return bamboo.getId();
    }

}
