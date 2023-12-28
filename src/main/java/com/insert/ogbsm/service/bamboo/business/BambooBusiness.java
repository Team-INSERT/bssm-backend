package com.insert.ogbsm.service.bamboo.business;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.service.bamboo.implement.BambooImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<AllowedBamboo> findAllAllowedBamboo(Pageable pageable) {
        return bambooImplement.readAllowed(pageable);
    }

    @Transactional(readOnly = true)
    public AllowedBamboo findMostRecentAllowedBamboo() {
        return bambooImplement.readMostRecentAllowed();
    }
}
