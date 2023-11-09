package com.insert.ogbsm.service.bamboo.business;

import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.service.bamboo.implement.BambooImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BambooBusiness {

    private final BambooImplement bambooImplement;

    public Long createBamboo(Bamboo bamboo) {
        bambooImplement.save(bamboo);
        return bamboo.getId();
    }

    @Transactional(readOnly = true)
    public Slice<AllowedBambooRes> findAllAllowedBamboo(Pageable pageable) {
        return bambooImplement.readAllowed(pageable);
    }

    @Transactional(readOnly = true)
    public AllowedBambooRes findMostRecentAllowedBamboo() {
        return bambooImplement.readMostRecentAllowed();
    }
}
